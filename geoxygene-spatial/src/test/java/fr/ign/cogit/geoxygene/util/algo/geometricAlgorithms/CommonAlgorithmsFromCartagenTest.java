package fr.ign.cogit.geoxygene.util.algo.geometricAlgorithms;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import fr.ign.cogit.geoxygene.api.spatial.coordgeom.IDirectPosition;
import fr.ign.cogit.geoxygene.api.spatial.coordgeom.IDirectPositionList;
import fr.ign.cogit.geoxygene.api.spatial.coordgeom.ILineString;
import fr.ign.cogit.geoxygene.api.spatial.geomprim.IRing;
import fr.ign.cogit.geoxygene.spatial.coordgeom.DirectPosition;
import fr.ign.cogit.geoxygene.spatial.coordgeom.DirectPositionList;
import fr.ign.cogit.geoxygene.spatial.coordgeom.GM_LineString;
import fr.ign.cogit.geoxygene.spatial.geomengine.GeometryEngine;
import fr.ign.cogit.geoxygene.spatial.geomprim.GM_Ring;
import fr.ign.cogit.geoxygene.util.algo.geomstructure.Vector2D;

public class CommonAlgorithmsFromCartagenTest {

  @Before
  public void setUp() throws Exception {
  }

  @Test
  public void testAreLinesSameDirection() {
    IDirectPosition p1 = new DirectPosition(0.0, 0.0);
    IDirectPosition p2 = new DirectPosition(10.0, 0.0);
    IDirectPosition p3 = new DirectPosition(20.0, 0.0);
    IDirectPosition p4 = new DirectPosition(30.0, 5.0);
    IDirectPosition p5 = new DirectPosition(40.0, 10.0);
    IDirectPositionList list1 = new DirectPositionList();
    IDirectPositionList list2 = new DirectPositionList();
    IDirectPositionList list3 = new DirectPositionList();
    list1.add(p1);
    list1.add(p2);
    list1.add(p3);
    list1.add(p4);
    list1.add(p5);
    ILineString line1 = new GM_LineString(list1);
    list2.add(p2);
    list2.add(p3);
    list2.add(p4);
    ILineString line2 = new GM_LineString(list2);
    list3.add(p4);
    list3.add(p3);
    list3.add(p2);
    ILineString line3 = new GM_LineString(list3);
    Assert.assertTrue(CommonAlgorithmsFromCartAGen.areLinesSameDirection(line1,
        line2));
    Assert.assertFalse(CommonAlgorithmsFromCartAGen.areLinesSameDirection(
        line1, line3));
  }

  @Test
  public void testOpenRingAtIndex() {
    IDirectPosition p1 = new DirectPosition(0.0, 0.0);
    IDirectPosition p2 = new DirectPosition(10.0, 0.0);
    IDirectPosition p3 = new DirectPosition(20.0, 0.0);
    IDirectPosition p4 = new DirectPosition(30.0, 5.0);
    IDirectPosition p5 = new DirectPosition(40.0, 10.0);
    IDirectPosition p6 = new DirectPosition(0.0, 10.0);
    IDirectPositionList list1 = new DirectPositionList();
    list1.add(p1);
    list1.add(p2);
    list1.add(p3);
    list1.add(p4);
    list1.add(p5);
    list1.add(p6);
    list1.add(p1);
    IRing line1 = new GM_Ring(new GM_LineString(list1));
    IDirectPositionList list2 = new DirectPositionList();
    list2.add(p3);
    list2.add(p4);
    list2.add(p5);
    list2.add(p6);
    list2.add(p1);
    list2.add(p2);
    ILineString line2 = new GM_LineString(list2);
    Assert.assertTrue(line2.equals(CommonAlgorithmsFromCartAGen
        .openRingAtIndex(line1, 2)));
  }

  @Test
  public void testInsertVertex() {
    IDirectPosition p1 = new DirectPosition(0.0, 0.0);
    IDirectPosition p2 = new DirectPosition(10.0, 10.0);
    IDirectPosition p3 = new DirectPosition(20.0, 20.0);
    IDirectPosition p4 = new DirectPosition(30.0, 30.0);
    IDirectPosition p5 = new DirectPosition(40.0, 50.0);
    IDirectPosition p6 = new DirectPosition(50.0, 55.0);
    IDirectPosition newVertex = new DirectPosition(25.0, 25.0);
    IDirectPositionList list1 = new DirectPositionList();
    list1.add(p1);
    list1.add(p2);
    list1.add(p3);
    list1.add(p4);
    list1.add(p5);
    list1.add(p6);
    ILineString line1 = new GM_LineString(list1);
    IDirectPositionList list2 = new DirectPositionList();
    list2.add(p1);
    list2.add(p2);
    list2.add(p3);
    list2.add(newVertex);
    list2.add(p4);
    list2.add(p5);
    list2.add(p6);
    ILineString line2 = new GM_LineString(list2);
    ILineString line3 = CommonAlgorithmsFromCartAGen.insertVertex(line1,
        newVertex);
    Assert.assertTrue(line2.equals(line3));
  }

  @Test
  public void testProjectionOrtho() {
    IDirectPosition point = new DirectPosition(1.0, 1.0);
    Vector2D direction = new Vector2D(0.0, 1.0);
    IDirectPositionList list = new DirectPositionList();
    IDirectPositionList list2 = new DirectPositionList();
    IDirectPosition p1 = new DirectPosition(2.0, 0.0);
    IDirectPosition p2 = new DirectPosition(2.0, 1.0);
    IDirectPosition p3 = new DirectPosition(2.0, 4.0);
    list.add(p1);
    list.add(p2);
    list.add(p3);
    list2.add(p1);
    list2.add(p3);
    ILineString line = new GM_LineString(list);
    ILineString line2 = new GM_LineString(list2);
    IDirectPosition proj = CommonAlgorithmsFromCartAGen.projectionOrtho(point,
        line, direction);
    Assert.assertTrue(proj.equals(new DirectPosition(2.0, 1.0)));
    Assert.assertTrue(CommonAlgorithmsFromCartAGen.projectionOrtho(point,
        line2, direction).equals(new DirectPosition(2.0, 1.0)));
  }

  @Test
  public void testGetSubLine() {
    IDirectPosition p1 = new DirectPosition(0.0, 0.0);
    IDirectPosition p2 = new DirectPosition(10.0, 10.0);
    IDirectPosition p3 = new DirectPosition(20.0, 20.0);
    IDirectPosition p4 = new DirectPosition(30.0, 30.0);
    IDirectPosition p5 = new DirectPosition(40.0, 50.0);
    IDirectPosition p6 = new DirectPosition(50.0, 55.0);
    ILineString line = new GM_LineString(p1, p2, p3, p4, p5, p6);
    IDirectPosition pt1 = new DirectPosition(5.0, 5.0);
    IDirectPosition pt2 = new DirectPosition(25.0, 25.0);
    IDirectPositionList list1 = new DirectPositionList();
    list1.add(pt1);
    list1.add(p2);
    list1.add(p3);
    list1.add(pt2);
    ILineString line1 = new GM_LineString(list1);
    IDirectPositionList list2 = new DirectPositionList();
    list2.add(p2);
    list2.add(p3);
    list2.add(p4);
    list2.add(p5);
    ILineString line2 = new GM_LineString(list2);
    ILineString line3 = CommonAlgorithmsFromCartAGen.getSubLine(line, pt1, pt2);
    ILineString line4 = CommonAlgorithmsFromCartAGen.getSubLine(line, p2, p5);

    Assert.assertTrue(line1.equals(line3));
    Assert.assertTrue(line2.equals(line4));
  }

  @Test
  public void testSplitLine() {
    IDirectPosition p1 = new DirectPosition(0.0, 0.0);
    IDirectPosition p2 = new DirectPosition(10.0, 10.0);
    IDirectPosition p3 = new DirectPosition(20.0, 20.0);
    IDirectPosition p4 = new DirectPosition(30.0, 30.0);
    IDirectPosition p5 = new DirectPosition(40.0, 50.0);
    IDirectPosition p6 = new DirectPosition(50.0, 55.0);
    IDirectPosition p7 = new DirectPosition(15.0, 15.0);
    ILineString line = new GM_LineString(p1, p2, p3, p4, p5, p6);
    IDirectPositionList list1 = new DirectPositionList();
    list1.add(p1);
    list1.add(p2);
    list1.add(p3);
    ILineString line1 = new GM_LineString(list1);
    IDirectPositionList list2 = new DirectPositionList();
    list2.add(p3);
    list2.add(p4);
    list2.add(p5);
    list2.add(p6);
    ILineString line2 = new GM_LineString(list2);
    IDirectPositionList list3 = new DirectPositionList();
    list3.add(p1);
    list3.add(p2);
    list3.add(p7);
    ILineString line3 = new GM_LineString(list3);
    IDirectPositionList list4 = new DirectPositionList();
    list4.add(p7);
    list4.add(p3);
    list4.add(p4);
    list4.add(p5);
    list4.add(p6);
    ILineString line4 = new GM_LineString(list4);

    GeometryEngine.init();
    ILineString[] lines = CommonAlgorithmsFromCartAGen.splitLine(line, p3,
        0.001);
    ILineString[] lines2 = CommonAlgorithmsFromCartAGen.splitLine(line, p7,
        0.001);

    Assert.assertTrue(line3.equals(lines2[0]));
    Assert.assertTrue(line4.equals(lines2[1]));
    Assert.assertTrue(line1.equals(lines[0]));
    Assert.assertTrue(line2.equals(lines[1]));
  }

}
