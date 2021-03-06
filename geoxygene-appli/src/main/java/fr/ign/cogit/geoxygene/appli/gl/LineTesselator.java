/*
 * This file is part of the GeOxygene project source files. GeOxygene aims at
 * providing an open framework which implements OGC/ISO specifications for the
 * development and deployment of geographic (GIS) applications. It is a open
 * source contribution of the COGIT laboratory at the Institut Géographique
 * National (the French National Mapping Agency). See:
 * http://oxygene-project.sourceforge.net Copyright (C) 2005 Institut
 * Géographique National This library is free software; you can redistribute it
 * and/or modify it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of the License,
 * or any later version. This library is distributed in the hope that it will be
 * useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser
 * General Public License for more details. You should have received a copy of
 * the GNU Lesser General Public License along with this library (see file
 * LICENSE if present); if not, write to the Free Software Foundation, Inc., 59
 * Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */
package fr.ign.cogit.geoxygene.appli.gl;

import static fr.ign.cogit.geoxygene.util.math.VectorUtil.addPoint2D;
import static fr.ign.cogit.geoxygene.util.math.VectorUtil.distance;
import static fr.ign.cogit.geoxygene.util.math.VectorUtil.dot;
import static fr.ign.cogit.geoxygene.util.math.VectorUtil.length;
import static fr.ign.cogit.geoxygene.util.math.VectorUtil.lineIntersection;
import static fr.ign.cogit.geoxygene.util.math.VectorUtil.mulDoublePoint2D;
import static fr.ign.cogit.geoxygene.util.math.VectorUtil.normalize;
import static fr.ign.cogit.geoxygene.util.math.VectorUtil.opposite;
import static fr.ign.cogit.geoxygene.util.math.VectorUtil.rotateVector;
import static fr.ign.cogit.geoxygene.util.math.VectorUtil.vector;

import java.awt.BasicStroke;
import java.awt.Shape;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.lwjgl.opengl.GL11;

import fr.ign.cogit.geoxygene.api.spatial.coordgeom.IDirectPositionList;
import fr.ign.cogit.geoxygene.api.spatial.coordgeom.IPolygon;
import fr.ign.cogit.geoxygene.api.spatial.geomprim.ICurve;
import fr.ign.cogit.geoxygene.api.spatial.geomprim.IRing;
import fr.ign.cogit.geoxygene.function.ConstantFunction;
import fr.ign.cogit.geoxygene.function.Function1D;
import fr.ign.cogit.geoxygene.function.FunctionEvaluationException;
import fr.ign.cogit.geoxygene.style.Stroke;
import fr.ign.cogit.geoxygene.util.gl.GLMesh;
import fr.ign.cogit.geoxygene.util.gl.GLSimpleVertex;
import fr.ign.cogit.geoxygene.util.math.BernsteinPolynomial;

/**
 * Tesselator class specialized in tesselating lines (closed or opened)
 * 
 * @author David
 * 
 */
public class LineTesselator {

    private static final Logger logger = Logger.getLogger(LineTesselator.class
            .getName()); // logger
    private static final double DEFAULT_AWT_MITERLIMIT = 10.; // 10 is the
    // default miter
    // limit defined
    // by
    // java.awt.BasiStroke
    private static final double anglePrecision = Math.PI / 40;
    private static final double epsilon = 1E-6;
    private static final int BEZIER_SAMPLE_COUNT = 20;
    private static final Point2D.Double[] emptyPoint2DArray = new Point2D.Double[] {};

    // /**
    // * generate the outline of a collection of polygons
    // *
    // * @param polygons
    // * @param stroke
    // * @param minX
    // * @param minY
    // * @return
    // */
    // public static GLSimpleComplex createPolygonOutlines(String id,
    // List<? extends IPolygon> polygons, Stroke stroke, double minX,
    // double minY) {
    // GLSimpleComplex primitive = new GLSimpleComplex(id, minX, minY);
    //
    // for (IPolygon polygon : polygons) {
    // createPolygonOutline(primitive, polygon, stroke, minX, minY);
    //
    // }
    // return primitive;
    // }

    public static void createPolygonOutline(GLSimpleComplex complex,
            IPolygon polygon, Stroke stroke, double minX, double minY) {
        try {
            tesselateRing(complex, polygon.getExterior(), new ConstantFunction(
                    stroke.getStrokeWidth()), stroke.getStrokeLineJoin(),
                    stroke.getStrokeLineCap(), DEFAULT_AWT_MITERLIMIT, minX,
                    minY);
        } catch (FunctionEvaluationException e) {
            logger.error(e);
        }
        for (IRing interior : polygon.getInterior()) {
            try {
                tesselateRing(complex, interior,
                        new ConstantFunction(stroke.getStrokeWidth()),
                        stroke.getStrokeLineJoin(), stroke.getStrokeLineCap(),
                        DEFAULT_AWT_MITERLIMIT, minX, minY);
            } catch (FunctionEvaluationException e) {
                logger.error(e);
            }
        }
    }

    public static GLSimpleComplex createPolygonOutline(String id,
            IPolygon polygon, Stroke stroke, double minX, double minY) {
        GLSimpleComplex complex = new GLSimpleComplex(id, minX, minY);
        createPolygonOutline(complex, polygon, stroke, minX, minY);
        return complex;
    }

    /**
     * Tesselation of an IRing (closed polyline)
     * 
     * @param complex
     * @param ring
     * @param getWidth
     * @param minX
     * @param minY
     * @throws FunctionEvaluationException
     */
    public static void tesselateRing(GLSimpleComplex complex, IRing ring,
            Function1D getWidth, int join, int cap, double miterLimit,
            double minX, double minY) throws FunctionEvaluationException {
        IDirectPositionList dlist = ring.coord();
        int pointCount = dlist.size();
        List<Point2D> polyline = new ArrayList<Point2D>();
        Point2D p0 = null;
        for (int i = 0; i < pointCount; ++i) {
            Point2D.Double pi = new Point2D.Double(dlist.get(i).getX() - minX,
                    dlist.get(i).getY() - minY);
            if (p0 == null || distance(p0, pi) > epsilon) {
                polyline.add(pi);
                p0 = pi;
            }
        }

        tesselateThickLine(complex, getWidth,
                polyline.toArray(emptyPoint2DArray), join, cap, miterLimit,
                true);
    }

    /**
     * @param complex
     * @param getWidth
     * @param polyline
     * @throws FunctionEvaluationException
     */
    public static void tesselateThickLine(GLSimpleComplex complex,
            Function1D getWidth, Point2D[] polyline, int join, int cap,
            double miterLimit, boolean closedLine)
            throws FunctionEvaluationException {
        complex.setMayOverlap(true);

        // Algo tessellation
        int size = polyline.length;
        int edgeCount = closedLine ? size : size - 1;
        if (polyline[0].equals(polyline[size - 1])) {
            // if the last point is the same as the first one, remove the last
            // from the list of points
            edgeCount -= 1;
            size -= 1;
        }
        float arcLength = 0;
        Point2D[] edges = new Point2D.Double[edgeCount];
        Point2D[] normals = new Point2D.Double[edgeCount];

        for (int edgeIndex = 0; edgeIndex < edgeCount; edgeIndex++) {
            Point2D p0 = polyline[edgeIndex];
            Point2D p1 = polyline[(edgeIndex + 1) % size];
            edges[edgeIndex] = new Point2D.Double(p1.getX() - p0.getX(),
                    p1.getY() - p0.getY());
            normals[edgeIndex] = normalize(new Point2D.Double(
                    -edges[edgeIndex].getY(), edges[edgeIndex].getX()));
            arcLength += length(edges[edgeIndex]);
        }

        GLMesh mesh = complex.addGLMesh(GL11.GL_TRIANGLES);
        float currentLength = 0;
        int vertexIndex0 = 0;
        int vertexIndex1 = 0;

        Point2D edge0 = edges[0];
        Point2D normal0 = normals[0];
        double l = getWidth.evaluate(currentLength / arcLength) / 2;
        Point2D sideVector = mulDoublePoint2D(l, normal0);
        Point2D vertex0 = addPoint2D(polyline[0], opposite(sideVector));
        Point2D vertex1 = addPoint2D(polyline[0], sideVector);
        vertexIndex0 = complex.addVertex(new GLSimpleVertex(vertex0));
        vertexIndex1 = complex.addVertex(new GLSimpleVertex(vertex1));
        if (!closedLine) { // initial cap
            createCap(complex, mesh, l, cap, vertexIndex0, vertexIndex1, edge0,
                    normal0, vertex0, vertex1, true);
        }

        // treat all segments (draw segment + join to the next segment)
        for (int currentEdgeIndex = 0; currentEdgeIndex < edgeCount; currentEdgeIndex++) {
            int nextEdgeIndex = (currentEdgeIndex + 1) % edgeCount;
            Point2D endPoint0 = polyline[(currentEdgeIndex + 1) % size];
            edge0 = edges[currentEdgeIndex];
            normal0 = normals[currentEdgeIndex];
            Point2D startPoint1 = endPoint0;
            Point2D normal1 = normals[nextEdgeIndex];
            Point2D edge1 = edges[nextEdgeIndex];

            // side to cap is in the same direction than normals ? then 1, else
            // -1
            int sideToCap = -1;
            double turnDirection = dot(edge0, normal1);
            double segmentLength = length(edge0);
            if (segmentLength < epsilon) {
                // if two points are at the same position, skip it
                continue;
            }
            currentLength += segmentLength;

            if (turnDirection > epsilon) {
                sideToCap = 1;
            } else if (turnDirection < -epsilon) {
                sideToCap = -1;
            } else {
                // if points are exactly aligned, we can go directly to the next
                // point.
                // if there is only two points in the line, add triangles
                if (edgeCount == 1) {
                    // last point
                    Point2D vertex2 = addPoint2D(endPoint0,
                            mulDoublePoint2D(-l, normal0));
                    Point2D vertex3 = addPoint2D(endPoint0,
                            mulDoublePoint2D(l, normal0));
                    int vertexIndex2 = complex.addVertex(new GLSimpleVertex(
                            vertex2));
                    int vertexIndex3 = complex.addVertex(new GLSimpleVertex(
                            vertex3));
                    // add triangles
                    mesh.addIndices(vertexIndex0, vertexIndex1, vertexIndex2);
                    mesh.addIndices(vertexIndex1, vertexIndex2, vertexIndex3);
                    vertexIndex0 = vertexIndex2;
                    vertexIndex1 = vertexIndex3;
                }
                continue;
            }
            l = getWidth.evaluate(currentLength / arcLength) / 2;

            // join between lines
            Point2D midPoint = null;
            // midPoint = addPoint2D(endPoint0, mulDoublePoint2D(-sideToCap*l,
            // addPoint2D(normal0, normal1)));
            midPoint = lineIntersection(
                    addPoint2D(endPoint0,
                            mulDoublePoint2D(-sideToCap * l, normal0)),
                    edge0,
                    addPoint2D(endPoint0,
                            mulDoublePoint2D(-sideToCap * l, normal1)), edge1);

            if (currentEdgeIndex == edgeCount - 1 && !closedLine) {
                // last point
                Point2D vertex2 = addPoint2D(endPoint0,
                        mulDoublePoint2D(-l, normal0));
                Point2D vertex3 = addPoint2D(endPoint0,
                        mulDoublePoint2D(l, normal0));
                int vertexIndex2 = complex
                        .addVertex(new GLSimpleVertex(vertex2));
                int vertexIndex3 = complex
                        .addVertex(new GLSimpleVertex(vertex3));
                // add triangles
                mesh.addIndices(vertexIndex0, vertexIndex1, vertexIndex2);
                mesh.addIndices(vertexIndex1, vertexIndex2, vertexIndex3);
                vertexIndex0 = vertexIndex2;
                vertexIndex1 = vertexIndex3;
            } else {

                // in AWT if the angle is too small, miter join is switched to
                // bevel join to avoid potential infinite miter point
                switch (join) {
                case BasicStroke.JOIN_MITER: {
                    Point2D miterPoint = lineIntersection(
                            addPoint2D(endPoint0,
                                    mulDoublePoint2D(sideToCap * l, normal0)),
                            edge0,
                            addPoint2D(endPoint0,
                                    mulDoublePoint2D(sideToCap * l, normal1)),
                            edge1);
                    if (distance(miterPoint, midPoint) < miterLimit * l * 2) {
                        // miter should be represented as bevel due to too small
                        // angle between lines
                        int miterVertexIndex = complex
                                .addVertex(new GLSimpleVertex(miterPoint));
                        int midVertexIndex = complex
                                .addVertex(new GLSimpleVertex(midPoint));
                        if (sideToCap == 1) {
                            mesh.addIndices(vertexIndex0, vertexIndex1,
                                    midVertexIndex);
                            mesh.addIndices(vertexIndex1, midVertexIndex,
                                    miterVertexIndex);
                            vertexIndex0 = midVertexIndex;
                            vertexIndex1 = miterVertexIndex;
                        } else {
                            mesh.addIndices(vertexIndex0, vertexIndex1,
                                    miterVertexIndex);
                            mesh.addIndices(vertexIndex1, miterVertexIndex,
                                    midVertexIndex);
                            vertexIndex0 = miterVertexIndex;
                            vertexIndex1 = midVertexIndex;
                        }
                        break;
                    }
                }
                case BasicStroke.JOIN_BEVEL: {
                    Point2D vertex2 = (sideToCap == 1) ? midPoint : addPoint2D(
                            endPoint0, mulDoublePoint2D(-l, normal0));
                    int vertexIndex2 = complex.addVertex(new GLSimpleVertex(
                            vertex2));
                    Point2D vertex3 = (sideToCap == -1) ? midPoint
                            : addPoint2D(endPoint0,
                                    mulDoublePoint2D(l, normal0));
                    Point2D vertex4 = (sideToCap == 1) ? addPoint2D(
                            startPoint1, mulDoublePoint2D(l, normal1))
                            : addPoint2D(startPoint1,
                                    mulDoublePoint2D(-l, normal1));
                    int vertexIndex3 = complex.addVertex(new GLSimpleVertex(
                            vertex3));
                    int vertexIndex4 = complex.addVertex(new GLSimpleVertex(
                            vertex4));
                    // add triangles
                    mesh.addIndices(vertexIndex0, vertexIndex1, vertexIndex2);
                    mesh.addIndices(vertexIndex1, vertexIndex2, vertexIndex3);
                    mesh.addIndices(vertexIndex2, vertexIndex4, vertexIndex3);
                    vertexIndex0 = (sideToCap == 1) ? vertexIndex2
                            : vertexIndex4;
                    vertexIndex1 = (sideToCap == 1) ? vertexIndex4
                            : vertexIndex3;
                }
                    break;
                case BasicStroke.JOIN_ROUND: {
                    Point2D centerPoint = endPoint0; // edges intersection
                    Point2D vertex2 = (sideToCap == 1) ? midPoint : addPoint2D(
                            endPoint0, mulDoublePoint2D(-l, normal0));
                    Point2D vertex3 = (sideToCap == -1) ? midPoint
                            : addPoint2D(endPoint0,
                                    mulDoublePoint2D(l, normal0));
                    Point2D vertex4 = (sideToCap == 1) ? addPoint2D(
                            startPoint1, mulDoublePoint2D(l, normal1))
                            : addPoint2D(startPoint1,
                                    mulDoublePoint2D(-l, normal1));
                    int vertexIndex2 = complex.addVertex(new GLSimpleVertex(
                            vertex2));
                    int vertexIndex3 = complex.addVertex(new GLSimpleVertex(
                            vertex3));
                    int vertexIndex4 = complex.addVertex(new GLSimpleVertex(
                            vertex4));
                    int midVertexIndex = complex.addVertex(new GLSimpleVertex(
                            midPoint));
                    // add triangles
                    mesh.addIndices(vertexIndex0, vertexIndex1, vertexIndex2);
                    mesh.addIndices(vertexIndex1, vertexIndex2, vertexIndex3);

                    Point2D border1 = vector(centerPoint,
                            (sideToCap == 1) ? vertex3 : vertex2);
                    Point2D border2 = vector(centerPoint, vertex4);
                    double alpha1 = Math.atan2(border1.getY(), border1.getX());
                    double alpha2 = Math.atan2(border2.getY(), border2.getX());
                    if (alpha1 < 0) {
                        alpha1 += 2 * Math.PI;
                    }
                    if (alpha2 < 0) {
                        alpha2 += 2 * Math.PI;
                    }
                    if (sideToCap == -1 && alpha2 < alpha1) {
                        alpha2 += Math.PI * 2;
                    } else if (sideToCap == 1 && alpha1 < alpha2) {
                        alpha2 -= Math.PI * 2;
                    }
                    int nbJoinPoints = (int) Math.abs((alpha2 - alpha1)
                            / anglePrecision);

                    if (sideToCap == 1) { // from vertex3 to vertex 4
                        if (nbJoinPoints == 0) {
                            mesh.addIndices(midVertexIndex, vertexIndex3,
                                    vertexIndex4);
                        } else {
                            int previousVertexIndex = vertexIndex3;
                            for (int n = 1; n <= nbJoinPoints; n++) { // round
                                // part
                                double angle = n * (alpha2 - alpha1)
                                        / nbJoinPoints;
                                int currentVertexIndex = complex
                                        .addVertex(new GLSimpleVertex(
                                                addPoint2D(
                                                        centerPoint,
                                                        rotateVector(border1,
                                                                angle))));
                                mesh.addIndices(midVertexIndex,
                                        previousVertexIndex, currentVertexIndex);
                                previousVertexIndex = currentVertexIndex;
                            }
                        }
                    } else {// from vertex2 to vertex 4
                        if (nbJoinPoints == 0) {
                            mesh.addIndices(midVertexIndex, vertexIndex2,
                                    vertexIndex4);
                        } else {
                            int previousVertexIndex = vertexIndex2;

                            for (int n = 1; n <= nbJoinPoints; n++) { // round
                                // part
                                double angle = n * (alpha2 - alpha1)
                                        / nbJoinPoints;
                                int currentVertexIndex = complex
                                        .addVertex(new GLSimpleVertex(
                                                addPoint2D(
                                                        centerPoint,
                                                        rotateVector(border1,
                                                                angle))));
                                mesh.addIndices(midVertexIndex,
                                        previousVertexIndex, currentVertexIndex);
                                previousVertexIndex = currentVertexIndex;
                            }
                        }
                    }

                    vertexIndex0 = (sideToCap == 1) ? vertexIndex2
                            : vertexIndex4;
                    vertexIndex1 = (sideToCap == 1) ? vertexIndex4
                            : vertexIndex3;
                }
                    break;
                }
            }
        }

        // end Cap
        if (!closedLine) {
            vertex0 = new Point2D.Double(complex.getVertices()
                    .get(vertexIndex0).getXYZ()[0], complex.getVertices()
                    .get(vertexIndex0).getXYZ()[1]);
            vertex1 = new Point2D.Double(complex.getVertices()
                    .get(vertexIndex1).getXYZ()[0], complex.getVertices()
                    .get(vertexIndex1).getXYZ()[1]);
            createCap(complex, mesh, l, cap, vertexIndex0, vertexIndex1, edge0,
                    normal0, vertex0, vertex1, false);
        }
    }

    /**
     * @param complex
     * @param getWidth
     * @param cap
     * @param arcLength
     * @param currentLength
     * @param vertexIndex0
     * @param vertexIndex1
     * @param edge0
     * @param normal0
     * @param vertex0
     * @param vertex1
     * @throws FunctionEvaluationException
     */
    private static void createCap(GLSimpleComplex complex, GLMesh mesh,
            double l, int cap, int vertexIndex0, int vertexIndex1,
            Point2D edge0, Point2D normal0, Point2D vertex0, Point2D vertex1,
            boolean startPoint) throws FunctionEvaluationException {
        Point2D sideVector;
        switch (cap) {
        case BasicStroke.CAP_BUTT:
            // BUTT is the default computation of start points
            break;
        case BasicStroke.CAP_SQUARE:
            Point2D dec = mulDoublePoint2D(startPoint ? -l : l,
                    normalize(edge0));
            // retrieve lasts points and move them
            GLSimpleVertex v0 = complex.getVertices().get(vertexIndex0);
            GLSimpleVertex v1 = complex.getVertices().get(vertexIndex1);
            v0.setXYZ(addPoint2D(new Point2D.Double(v0.getXYZ()[0],
                    v0.getXYZ()[1]), dec));
            v1.setXYZ(addPoint2D(new Point2D.Double(v1.getXYZ()[0],
                    v1.getXYZ()[1]), dec));
            break;
        case BasicStroke.CAP_ROUND: {
            Point2D splitVertex = mulDoublePoint2D(0.5,
                    addPoint2D(vertex0, vertex1));
            int splitVertexIndex = complex.addVertex(new GLSimpleVertex(
                    splitVertex));
            int nbCapPoints = Math.max(3, (int) (Math.PI / anglePrecision));
            sideVector = mulDoublePoint2D(l, normal0);
            int previousVertexIndex = vertexIndex1;
            for (int n = 1; n <= nbCapPoints; n++) {
                double angle = n * Math.PI / nbCapPoints;
                if (!startPoint) {
                    angle *= -1;
                }
                int currentVertexIndex = complex
                        .addVertex(new GLSimpleVertex(addPoint2D(splitVertex,
                                rotateVector(sideVector, angle))));
                mesh.addIndices(splitVertexIndex, previousVertexIndex,
                        currentVertexIndex);
                previousVertexIndex = currentVertexIndex;
            }
        }
            break;
        }
    }

    // // compute control points to estimate at best a circle with a cubic
    // interpolation
    // //
    // http://d.krauss.free.fr/documents/Transverses/Bezier/Arc_cercle/Arc_Cercle.htm
    // double alpha = Math.abs(Math.atan2(edge1.getY() - edge0.getY(),
    // edge1.getX() - edge0.getX()));
    // double k1 = 4. / 3. * Math.tan(alpha / 4.);
    // k1 *= getWidth.evaluate(currentLength / arcLength) / 2;
    // System.err.println("k=" + k1);
    // float t = n / (float) nbJoinPoints;
    // float px, py;
    // if (sideToCap == 1) {
    // Point2D c1 = addPoint2D(vertex3, mulDoublePoint2D(k1, normalize(edge0)));
    // Point2D c2 = addPoint2D(vertex4, mulDoublePoint2D(-k1,
    // normalize(edge1)));
    // px = (float) GLComplexFactory.interpolateCubic(vertex3.getX(), c1.getX(),
    // c2.getX(), vertex4.getX(), t);
    // py = (float) GLComplexFactory.interpolateCubic(vertex3.getY(), c1.getY(),
    // c2.getY(), vertex4.getY(), t);
    // } else {
    // Point2D c1 = addPoint2D(vertex2, mulDoublePoint2D(k1, normalize(edge0)));
    // Point2D c2 = addPoint2D(vertex4, mulDoublePoint2D(-k1,
    // normalize(edge1)));
    // px = (float) GLComplexFactory.interpolateCubic(vertex2.getX(), c1.getX(),
    // c2.getX(), vertex4.getX(), t);
    // py = (float) GLComplexFactory.interpolateCubic(vertex2.getY(), c1.getY(),
    // c2.getY(), vertex4.getY(), t);
    //
    // }
    // int fanVertexIndex = complex.addVertex(new GLSimpleVertex(px, py, 0f));
    // roundJoinMesh.addIndices(fanVertexIndex); // triangle fan center

    /**
     * Tesselation of an ICurve (closed or open polyline or polycurve)
     * 
     * @param complex
     * @param ring
     * @param getWidth
     * @param minX
     * @param minY
     * @throws FunctionEvaluationException
     */
    private static void tesselateThickLine(GLSimpleComplex complex,
            ICurve line, Function1D getWidth, int join, int cap,
            double miterLimit, double minX, double minY)
            throws FunctionEvaluationException {
        IDirectPositionList dlist = line.coord();
        tesselateThickLine(complex, dlist, getWidth, join, cap, miterLimit,
                minX, minY);
    }

    /**
     * @param complex
     * @param dlist
     * @param getWidth
     * @param join
     * @param cap
     * @param miterLimit
     * @param minX
     * @param minY
     * @throws FunctionEvaluationException
     */
    public static void tesselateThickLine(GLSimpleComplex complex,
            IDirectPositionList dlist, Function1D getWidth, int join, int cap,
            double miterLimit, double minX, double minY)
            throws FunctionEvaluationException {
        int pointCount = dlist.size();
        Point2D[] polyline = new Point2D.Double[pointCount];
        for (int i = 0; i < pointCount; ++i) {
            polyline[i] = new Point2D.Double(dlist.get(i).getX() - minX, dlist
                    .get(i).getY() - minY);
        }

        tesselateThickLine(complex, getWidth, polyline, join, cap, miterLimit,
                false);
    }

    /**
     * Tesselation of an ICurve (closed or open polyline or polycurve)
     * 
     * @param complex
     * @param ring
     * @param getWidth
     * @param minX
     * @param minY
     * @throws FunctionEvaluationException
     */
    private static void tesselateThickLine(GLSimpleComplex complex,
            Shape shape, Function1D getWidth, int join, int cap,
            double miterLimit, double minX, double minY)
            throws FunctionEvaluationException {
        PathIterator iter = shape.getPathIterator(null);
        float lastX = 0;
        float lastY = 0;
        float lastMoveX = 0;
        float lastMoveY = 0;
        float[] coords = new float[6];
        List<Point2D> polyline = new ArrayList<Point2D>();
        boolean closed = false;
        while (!iter.isDone()) {

            int currentSegment = iter.currentSegment(coords);
            coords[0] -= minX;
            coords[1] -= minY;
            coords[2] -= minX;
            coords[3] -= minY;
            coords[4] -= minX;
            coords[5] -= minY;
            switch (currentSegment) {

            case PathIterator.SEG_MOVETO: // 1 point (2 vars) in coords
                lastX = lastMoveX = coords[0];
                lastY = lastMoveY = coords[1];
                break;
            case PathIterator.SEG_LINETO: // 1 point
                polyline.add(new Point2D.Double(coords[0], coords[1]));
                lastX = coords[0];
                lastY = coords[1];
                break;

            case PathIterator.SEG_QUADTO: // 2 points (1 control point)
                for (int i = 1; i <= BEZIER_SAMPLE_COUNT; i++) {
                    float t = i / (float) BEZIER_SAMPLE_COUNT;

                    double px = BernsteinPolynomial.evalQuadratic(lastX,
                            coords[0], coords[2], t);
                    double py = BernsteinPolynomial.evalQuadratic(lastY,
                            coords[1], coords[3], t);
                    polyline.add(new Point2D.Double(px, py));
                }
                lastX = coords[2];
                lastY = coords[3];
                break;

            case PathIterator.SEG_CUBICTO: // 3 points (2 control points)
                // System.err.println("CUBIC FROM " + lastX + "x" + lastY +
                // " TO " + coords[4] + "x" + coords[5]);
                for (int i = 1; i <= BEZIER_SAMPLE_COUNT; i++) {
                    float t = i / (float) BEZIER_SAMPLE_COUNT;

                    double px = BernsteinPolynomial.evalCubic(lastX, coords[0],
                            coords[2], coords[4], t);
                    double py = BernsteinPolynomial.evalCubic(lastY, coords[1],
                            coords[3], coords[5], t);
                    polyline.add(new Point2D.Double(px, py));
                }
                lastX = coords[4];
                lastY = coords[5];
                break;

            case PathIterator.SEG_CLOSE:
                lastX = lastMoveX;
                lastY = lastMoveY;
                closed = true;
                break;
            }
            iter.next();
        }

        tesselateThickLine(complex, getWidth,
                polyline.toArray(new Point2D[] {}), join, cap, miterLimit,
                closed);
    }

    /**
     * Curve tesselation
     * 
     * @param curves
     * @param stroke
     * @param minX
     * @param minY
     * @return
     */
    public static GLSimpleComplex createThickLine(String id,
            List<? extends ICurve> curves, Stroke stroke, double minX,
            double minY) {
        GLSimpleComplex complex = new GLSimpleComplex(id, minX, minY);

        for (ICurve curve : curves) {
            try {
                tesselateThickLine(complex, curve,
                        new ConstantFunction(stroke.getStrokeWidth()),
                        stroke.getStrokeLineJoin(), stroke.getStrokeLineCap(),
                        DEFAULT_AWT_MITERLIMIT, minX, minY);
            } catch (FunctionEvaluationException e) {
                logger.error(e);
            }
        }
        return complex;
    }

    /**
     * Curve tesselation
     * 
     * @param curves
     * @param stroke
     * @param minX
     * @param minY
     * @return
     */
    public static GLSimpleComplex createThickLine(String id, ICurve curve,
            Stroke stroke, double minX, double minY) {
        GLSimpleComplex complex = new GLSimpleComplex(id, minX, minY);

        try {
            tesselateThickLine(complex, curve,
                    new ConstantFunction(stroke.getStrokeWidth()),
                    stroke.getStrokeLineJoin(), stroke.getStrokeLineCap(),
                    DEFAULT_AWT_MITERLIMIT, minX, minY);
        } catch (FunctionEvaluationException e) {
            logger.error(e);
        }
        return complex;
    }

    /**
     * Curve tesselation
     * 
     * @param curves
     * @param stroke
     * @param minX
     * @param minY
     * @return
     */
    public static GLSimpleComplex createThickLine(String id, Shape shape,
            Stroke stroke, double minX, double minY) {
        GLSimpleComplex complex = new GLSimpleComplex(id, minX, minY);

        try {
            tesselateThickLine(complex, shape,
                    new ConstantFunction(stroke.getStrokeWidth()),
                    stroke.getStrokeLineJoin(), stroke.getStrokeLineCap(),
                    DEFAULT_AWT_MITERLIMIT, minX, minY);
        } catch (FunctionEvaluationException e) {
            logger.error(e);
        }
        return complex;
    }

}
