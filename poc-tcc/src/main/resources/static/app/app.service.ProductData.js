(function(){
   var ProductDataService = function($http){
       var address = "http://" + location.host + '/product';

       var getData = function(searchTerm,page,size){
          return new Promise(function(resolve,reject){
              $http.get(address,{
                  params:{
                      searchTerm: searchTerm,
                      page: page,
                      size :size
                  }
              }).then(function(response){
                  resolve(response.data)
              },function(reason){
                  reject("Erro na comunicação com o servidor ao buscar dados de produtos");
              });
          });
       };

       return{
           getData : getData
       }
   }

   angular.module("pocWebFrontEnd").factory("ProductDataService",ProductDataService);
})();