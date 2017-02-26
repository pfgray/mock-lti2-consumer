app.service('sampleToolsService', function() {

  function getTools(){
    if(window.localStorage) {
      var storedTools = JSON.parse(window.localStorage.getItem('ltiTools'));
      if(storedTools === null){
        storedTools = [];
        setTools(storedTools);
        return storedTools;
      } else {
        return storedTools;
      }
    } else {
      return [];
    }
  }
  function setTools(tools){
    if(window.localStorage){
      window.localStorage.setItem('ltiTools', angular.toJson(tools));
    }
  }

  var tools = getTools();
  return {
    getTools: function(){
      return _.cloneDeep(tools); //new array
    },
    addOrSetTool: function(tool){
      //match keys by url & key
      var currentToolIndex = _.findIndex(tools, function(t){
        return t.url === tool.url && t.key === tool.key;
      });
      if(currentToolIndex === -1){
        tools.unshift(tool);
      } else {
        tools[currentToolIndex] = tool;
      }
      //tools = tools;
      console.log('setting tools: ', tools);
      setTools(tools);
    }
  };

});