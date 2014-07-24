/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

app.controller('ToolsController', ['$scope', '$http', function($scope, $http) {
    
    var AddedTool = function(tool){
        tool.button = {
            label:"Register Tool",
            icon:"fa-wrench",
            onClick:function(){
                alert('added tool');
            }
        };
        tool.liClass = 'list-group-item-warning';
        return tool;
    };
    var RegisteredTool = function(tool){
        tool.button = {
            label:"Launch Tool",
            icon:"fa-rocket",
            onClick:function(){
                alert('registered tool');
            }
        };
        tool.liClass = 'list-group-item-success';
        return tool;
    };
    var FailedTool = function(tool){
        tool.button = {
            label:"Re-Register Tool",
            icon:"fa-wrench",
            onClick:function(){
                alert('failed tool');
            }
        };
        tool.liClass = 'list-group-item-danger';
        return tool;
    }
        
    $scope.loading = true;
    $http({method: 'GET', url: 'api/tools'})
    .success(function(data, status, headers, config) {
        $scope.tools = [];
        for(var i=0; i<data.length; i++){
            if(data[i].state === 'registered'){
                $scope.tools.push(new RegisteredTool(data[i]));
            } else if (data[i].state === 'added'){
                $scope.tools.push(new AddedTool(data[i]));
            } else if (data[i].state === 'failed'){
                $scope.tools.push(new FailedTool(data[i]));
            }
        }
        $scope.loading = false;
    })
    .error(function(data, status, headers, config) {
        $scope.error = true;
        $scope.loading = false;
    });
    
    $scope.addTool = function(name, registrationUrl){
        $http.post('api/tools', {
            label:name,
            registerUrl:registrationUrl
        })
        .success(function(data, status, headers, config) {
            alert('got back:', data);
        })
        .error(function(data, status, headers, config) {
            alert('ERROR!' + data);
            console.error('ERROR!', data);
        });
    };
    
}]);
