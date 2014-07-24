/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.paulgray.mocklti2.tools;

import java.util.List;

/**
 *
 * @author paul
 */
public interface LtiToolService {
    
    public LtiTool getToolForId(String id);

    public List<LtiTool> getAll();
    
}
