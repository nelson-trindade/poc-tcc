(function(){

    var ClientDataService = function($http){
        var address= "http://" + location.host + "/client";

        var getData = function(searchTerm,page,size){
            return new Promise(function(resolve,reject){
                $http.post(address,{
                    params:{
                        searchTerm: searchTerm,
                        page: page,
                        size: size
                    }
                }).then(function(response){
                    resolve(response.data);
                },function(reason){
                    reject('Erro na comunicação com o servidor ao buscar dados de clientes');
                });
            });
        };

        return {
            getData : getData
        }
    };

    angular.module("pocWebFrontEnd").factory("ClientDataService",ClientDataService);
})();