(function(){

    var SalesService = function($http){
        var address = "http://" + location.host + "/sales/salesOrder";

        var createSalesOrder = function(logisticDoc){
            return new Promise(function(resolve,reject){
                $http.post(address,logisticDoc)
                .then(function(response){
                    resolve(response.data);
                },function(reason){
                    reject(reason);
                })
            });
        };

        return{
            createSalesOrder : createSalesOrder
        }
    };

    angular.module("pocWebFrontEnd").factory("SalesService",SalesService);

})();