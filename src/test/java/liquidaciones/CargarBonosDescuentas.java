/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package liquidaciones;

import core.InitialContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import workcenter.negocio.LogicaTest;

/**
 *
 * @author claudio
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class CargarBonosDescuentas extends InitialContext {
    
    @Autowired
    LogicaTest logicaTest;
    
    @Test
    public void cargar() {
        logicaTest.cargar();
    }
}
