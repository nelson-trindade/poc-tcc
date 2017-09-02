(function(){
    var mainModule = angular.module("pocWebFrontEnd",["ngRoute"]);
    
    mainModule.config(function($routeProvider,$locationProvider){
        $routeProvider.when('/reverse',
        {
            templateUrl: 'app/views/reverseLogistic.html',
            controller: "reverseLogisticController"
        }).when('/',{
            templateUrl: 'app/views/initialContent.html'
        }).otherwise({
            redirectTo: '/'
        });
        $locationProvider.html5Mode(true);
    });
})();