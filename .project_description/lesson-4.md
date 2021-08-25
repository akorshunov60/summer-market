# Урок 4. homeEx-4

# 1. Добавить к магазину возможность выгрузки всех товаров
#    и отдельных товаров по id через SOAP.

1. Вносим изменения в pom.xml в dependency и в plugin:
    
   <dependency>
   		<groupId>org.springframework.boot</groupId>
   		<artifactId>spring-boot-starter-web-services</artifactId>
   </dependency>
   <dependency>
   		<groupId>javax.xml.bind</groupId>
   		<artifactId>jaxb-api</artifactId>
   		<version>2.3.1</version>
   </dependency>
   <dependency>
   		<groupId>org.glassfish.jaxb</groupId>
   		<artifactId>jaxb-runtime</artifactId>
   		<version>2.3.1</version>
   </dependency>
   <dependency>
   		<groupId>wsdl4j</groupId>
   		<artifactId>wsdl4j</artifactId>
   		<version>1.6.2</version>
   </dependency>

   		<plugin>
   			<groupId>org.codehaus.mojo</groupId>
   			<artifactId>jaxb2-maven-plugin</artifactId>
   			<version>2.5.0</version>
   			<executions>
   				<execution>
   					<id>xjc</id>
   					<goals>
   						<goal>xjc</goal>
   					</goals>
   				</execution>
   			</executions>
   			<configuration>
   				<sources>
   					<source>${project.basedir}/src/main/resources/categories.xsd</source>
   					<source>${project.basedir}/src/main/resources/products.xsd</source>
   				</sources>
				</configuration>
			</plugin>

2. Создаем файл с описанием схемы products.xsd.

3. По созданной схеме products.xsd с помощью maven плагина jaxb2:xjc
   запускаем процесс генерации SOAP классов и переносим созданный пакет
   products из target в root проекта в пакет soap.
   
4. Переименовываем наш класс для работы с БД Product в ProductEntity.

5. Создаем новый класс WebServiceConfig:

   @EnableWs
   @Configuration
   public class WebServiceConfig extends WsConfigurerAdapter {
   @Bean
   public ServletRegistrationBean messageDispatcherServlet(ApplicationContext applicationContext) {
   MessageDispatcherServlet servlet = new MessageDispatcherServlet();
   servlet.setApplicationContext(applicationContext);
   servlet.setTransformWsdlLocations(true);
   return new ServletRegistrationBean(servlet, "/ws/*");
   }

   // http://localhost:8189/summer/ws/categories.wsdl:
   @Bean(name = "categories")
   public DefaultWsdl11Definition categoriesWsdl11Definition(XsdSchema categoriesSchema) {
   DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
   wsdl11Definition.setPortTypeName("CategoriesPort");
   wsdl11Definition.setLocationUri("/ws");
   wsdl11Definition.setTargetNamespace("http://www.geekbrains.ru/summer/ws/categories");
   wsdl11Definition.setSchema(categoriesSchema);
   return wsdl11Definition;
   }

   // http://localhost:8189/summer/ws/products.wsdl:
   @Bean(name = "products")
   public DefaultWsdl11Definition productsWsdl11Definition(XsdSchema productsSchema) {
   DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
   wsdl11Definition.setPortTypeName("ProductsPort");
   wsdl11Definition.setLocationUri("/ws");
   wsdl11Definition.setTargetNamespace("http://www.geekbrains.ru/summer/ws/products");
   wsdl11Definition.setSchema(productsSchema);
   return wsdl11Definition;
   }

   @Bean
   public XsdSchema categoriesSchema() {
   return new SimpleXsdSchema(new ClassPathResource("categories.xsd"));
   }

   @Bean
   public XsdSchema productsSchema() {
   return new SimpleXsdSchema(new ClassPathResource("products.xsd"));
   }
   }
   
6. Вносим изменения в класс ProductService:

       public static final Function<ProductEntity, Product> functionEntityToSoap = pre -> {
        Product pr = new Product();
        pr.setId(pre.getId());
        pr.setTitle(pre.getTitle());
        pr.setCategoryTitle(pre.getCategoryEntity().getTitle());
        pr.setPrice(pre.getPrice());
        return pr;
   };

   public List<Product> getAllProducts() {
   return productRepository
   .findAll()
   .stream()
   .map(functionEntityToSoap)
   .collect(Collectors.toList());
   }

   public Product getById(long id) {
   return productRepository
   .findById(id)
   .map(functionEntityToSoap)
   .orElseThrow(() -> new ResourceNotFoundException("Product not found, id: " + id));
   }
   
7. Создаем новый класс ProductEndpoint:

   @Endpoint
   @RequiredArgsConstructor
   public class ProductEndpoint {

   private static final String NAMESPACE_URI = "http://www.geekbrains.ru/summer/ws/products";
   private final ProductService prdService;

   @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getProductByIdRequest")
   @ResponsePayload
   public GetProductByIdResponse getProductById(@RequestPayload GetProductByIdRequest request) {
   GetProductByIdResponse response = new GetProductByIdResponse();
   response.setProduct(prdService.getById(request.getId()));
   return response;
   }

   /*
   Пример запроса: POST http://localhost:8189/summer/ws

        <soapenv:Envelope   xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
                            xmlns:f="http://www.geekbrains.ru/summer/ws/products">
            <soapenv:Header/>
            <soapenv:Body>
                <f:getAllProductsRequest/>
            </soapenv:Body>
        </soapenv:Envelope>
   */

   @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllProductsRequest")
   @ResponsePayload
   public GetAllProductsResponse getAllProducts(@RequestPayload GetAllProductsRequest request) {
   GetAllProductsResponse response = new GetAllProductsResponse();
   prdService.getAllProducts().forEach(response.getProducts()::add);
   return response;
   }
   }
   
8. После запуска приложения с помощью Postman отправляем Post запрос (см. пример выше)
   и получаем ответ (файл response_product.xml приложен к проекту).
   
9. В качестве дополнительного задания реализовал тот же самый подход
   для класса Category (файл response_category.xml приложен к проекту).