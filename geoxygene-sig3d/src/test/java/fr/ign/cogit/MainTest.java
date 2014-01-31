package fr.ign.cogit;

import junit.framework.Test;
import junit.framework.TestSuite;
import junit.textui.TestRunner;
import fr.ign.cogit.calculation.TestBooleanOperators;
import fr.ign.cogit.calculation.TestCalculation3D;
import fr.ign.cogit.calculation.TestProximity;
import fr.ign.cogit.calculation.TestUtil;
import fr.ign.cogit.convert.geom.TestFromGeomToSurface;
import fr.ign.cogit.convert.geom.TestFromPolygonToLineString;
import fr.ign.cogit.convert.transform.TestExtrusion2DObject;
import fr.ign.cogit.equation.TestPlanEquation;

//--------------------------------------------------------------------------------
// Class containing main function to launch test suite
// Date : 28/01/2014
// Yann MENEROUX (yann.meneroux@ign.fr)
//--------------------------------------------------------------------------------

public class MainTest {

	
	public static Test suite(){
		
		TestSuite suite = new TestSuite("All 3D tests");
		
		suite.addTestSuite(TestBooleanOperators.class);
		suite.addTestSuite(TestCalculation3D.class);
		suite.addTestSuite(TestProximity.class);
		suite.addTestSuite(TestUtil.class);
		suite.addTestSuite(TestFromGeomToSurface.class);
		suite.addTestSuite(TestFromPolygonToLineString.class);
		suite.addTestSuite(TestExtrusion2DObject.class);
		suite.addTestSuite(TestPlanEquation.class);
		
		return suite;
		
	}
	
	public static void main(final String[] args){
		
		TestRunner.run(suite());
		
	}
		
}