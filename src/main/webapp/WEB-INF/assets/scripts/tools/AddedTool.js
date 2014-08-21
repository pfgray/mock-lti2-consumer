/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
app.factory('AddedTool', [function () {
    
    var AddedTool = function(tool){
        tool.button = {
            label:"Register Tool",
            icon:"fa-wrench",
            onClick:function(){
                var params = {
                    reg_key:"reg_key",
                    reg_password:"reg_password",
                    tc_profile_url:window.web_context_url + "api/profile",
                    launch_presentation_return_url:window.web_context_url,
                    lti_message_type:"lti-tool-proxy-registration-request"
                }
                
                var form = document.createElement("form");
                form.setAttribute("method", "GET");
                form.setAttribute("action", tool.registerUrl);

                for(var key in params) {
                    if(params.hasOwnProperty(key)) {
                        var hiddenField = document.createElement("input");
                        hiddenField.setAttribute("type", "hidden");
                        hiddenField.setAttribute("name", key);
                        hiddenField.setAttribute("value", params[key]);

                        form.appendChild(hiddenField);
                     }
                }

                document.body.appendChild(form);
                form.submit();
            }
        };
        tool.liClass = 'list-group-item-warning';
        return tool;
    };
    
    return AddedTool;
        
}]);

