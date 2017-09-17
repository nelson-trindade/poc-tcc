(function(){
    var mainModule = angular.module("pocWebFrontEnd",["ngRoute"]);
    
    mainModule.config(function($routeProvider,$locationProvider){
        $routeProvider.when('/reverse',
        {
            templateUrl: 'app/views/reverseLogistic.html',
            controller: "reverseLogisticController"
        }).when('/sales/salesOrder/create',
        {
            templateUrl:'app/views/createSalesOrder.html',
            controller:'createSalesOrderController'
        }).when('/',{
            templateUrl: 'app/views/initialContent.html'
        }).otherwise({
            redirectTo: '/'
        });
        $locationProvider.html5Mode(true);
    });
})();