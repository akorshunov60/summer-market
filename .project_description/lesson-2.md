# Урок 2. homeEx-2

## 1. Добавить фильтр по максимальной цене:

1. Добавляем в класс ProductSpecifications метод:
   public static Specification<Product> priceLessThanOrEqualTo(BigDecimal maxPrice) {...}
   
2. В классе ProductController в метод public Page<ProductDto> findAll()
   добавляем @RequestParam(name = "max_price", required = false) BigDecimal maxPrice)
   и условие:
   if(maxPrice != null) {
   spec = spec.and(ProductSpecifications.priceLessThanOrEqualTo(maxPrice));
   }

## 2. Убрать построение спецификаций куда-нибудь из контроллера:

1. Добавляем в класс ProductService метод:
   
   public Specification<Product> buildSpecification(BigDecimal minPrice, BigDecimal maxPrice, String title) {

        Specification<Product> specification = Specification.where(null);

        if(minPrice != null) {
            specification = specification.and(ProductSpecifications.priceGreaterOrEqualThan(minPrice));
        }
        if(maxPrice != null) {
            specification = specification.and(ProductSpecifications.priceLessThanOrEqualTo(maxPrice));
        }
        if(title != null) {
            specification = specification.and(ProductSpecifications.titleLike(title));
        }
        return specification;
   }

2. В классе ProductController метод public Page<ProductDto> findAll() приводим к виду:

   @GetMapping
   public Page<ProductDto> findAll(@RequestParam(name = "p", defaultValue = "1") int pageIndex,
   @RequestParam(name = "min_price", required = false) BigDecimal minPrice,
   @RequestParam(name = "max_price", required = false) BigDecimal maxPrice,
   @RequestParam(name = "title", required = false) String title) {

        return productService.findPage(pageIndex - 1, 5,
                productService.buildSpecification(minPrice, maxPrice, title)).map(ProductDto::new);
   }

## 3.Добавить форму фильтра на фронт:

1. В файле products.js модифицируем функцию loadPage:

       $scope.loadPage = function (pageIndex = 1) {
        $http({
            url: contextPath + '/api/v1/products',
            method: 'GET',
            params: {
                min_price: $scope.filter ? $scope.filter.min_price : null,
                max_price: $scope.filter ? $scope.filter.max_price : null,
                title: $scope.filter ? $scope.filter.title : null,
                'p': pageIndex

            }
        }).then(function (response) {
            $scope.productsPage = response.data;
            $scope.navList = $scope.generatePagesIndexes(1, $scope.productsPage.totalPages);
            console.log(response.data);
        });
   };

2. В файл products.html добавляем форму фильтра:

       <form novalidate ng-submit="loadPage()">
        <div class="form-group col-md-1">
            <label for="minPriceField">min Price</label>
            <input class="form-control" type="text" ng-model="filter.min_price" id="minPriceField">
        </div>
        <p></p>
        <div class="form-group col-md-1">
            <label for="maxPriceField">max Price</label>
            <input class="form-control" type="text" ng-model="filter.max_price" id="maxPriceField">
        </div>
        <p></p>
        <div class="form-group col-md-3">
            <label for="titleField">part Title</label>
            <input class="form-control" type="text" ng-model="filter.title" id="titleField">
        </div>
        <p></p>
        <div>
            <button class="btn btn-primary" type="submit">Apply</button>
        </div>
      </form>
