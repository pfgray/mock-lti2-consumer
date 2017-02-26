app.service('sampleToolsService', function() {

  function getTools(){
    if(window.localStorage) {
      var storedTools = JSON.parse(window.localStorage.getItem('ltiTools'));

      console.log('fetching tools? ', storedTools);
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
      console.log('setting tools: ', tools);
      window.localStorage.setItem('ltiTools', angular.toJson(tools));
    }
  }

  return {
    getTools: function(){
      return _.cloneDeep(getTools()); //new array
    },
    addOrSetTool: function(tool){
      var tools = this.getTools();
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
      setTools(tools);
    },
    removeTool: function(tool) {
      setTools(this.getTools().filter(function(t){
        return !(t.url === tool.url && t.key === tool.key);
      }));
    }
  };

});