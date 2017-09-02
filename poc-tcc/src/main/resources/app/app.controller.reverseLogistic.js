(function(){
    
    var mainModule = angular.module("pocWebFrontEnd");

    var reverseLogisticController = function($scope){
        $scope.openItemFormFlag = false;
        $scope.product = {};
        $scope.product.measureUnit='';
        $scope.productSearchList = [];


        $scope.addRow = function(){
            $scope.openItemFormFlag = true;
        }

        $scope.closeItemForm = function(){
            $scope.openItemFormFlag = false;
        }
    };

    mainModule.controller("reverseLogisticController",
    ["$scope",reverseLogisticController]);

})();