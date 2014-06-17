/*******************************************************************************
 * This file is part of the GeOxygene project source files.
 * 
 * GeOxygene aims at providing an open framework which implements OGC/ISO
 * specifications for the development and deployment of geographic (GIS)
 * applications. It is a open source contribution of the COGIT laboratory at the
 * Institut Géographique National (the French National Mapping Agency).
 * 
 * See: http://oxygene-project.sourceforge.net
 * 
 * Copyright (C) 2005 Institut Géographique National
 * 
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or any later version.
 * 
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library (see file LICENSE if present); if not, write to the
 * Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA
 * 02111-1307 USA
 *******************************************************************************/

package fr.ign.cogit.geoxygene.style.expressive;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * @author JeT
 * 
 */
@XmlAccessorType(XmlAccessType.NONE)
public class StrokeTextureExpressiveRendering extends ExpressiveRendering {

    @XmlElement(name = "PaperTexture")
    private String paperTextureFilename = "./src/main/resources/test/app/papers/black-normalized.png";
    @XmlElement(name = "BrushTexture")
    private String brushTextureFilename = "./src/main/resources/test/app/brushes/chalk2-100-200.png";
    @XmlElement(name = "BrushStartLength")
    private int brushStartLength = 100;
    @XmlElement(name = "BrushEndLength")
    private int brushEndLength = 200;
    @XmlElement(name = "SampleSize")
    private double sampleSize = 2.;
    @XmlElement(name = "minAngle")
    private double minAngle = 1.5;
    @XmlElement(name = "BrushAspectRatio")
    private double brushSize = 8;
    @XmlElement(name = "PaperScaleFactor")
    private double paperScaleFactor = .5;
    @XmlElement(name = "PaperRoughness")
    private double paperDensity = 0.7;
    @XmlElement(name = "BrushRoughness")
    private double brushDensity = 1.9;
    @XmlElement(name = "StrokePressure")
    private double strokePressure = 2.64;
    @XmlElement(name = "StrokeSoftness")
    private double sharpness = 0.1;
    @XmlElement(name = "StrokePressureVariationAmplitude")
    private double strokePressureVariationAmplitude = .32;
    @XmlElement(name = "StrokePressureVariationWavelength")
    private double strokePressureVariationWavelength = 1. / .025;
    @XmlElement(name = "StrokeShiftVariationAmplitude")
    private double strokeShiftVariationAmplitude = .92;
    @XmlElement(name = "StrokeShiftVariationWavelength")
    private double strokeShiftVariationWavelength = 1. / .05;
    @XmlElement(name = "StrokeThicknessVariationAmplitude")
    private double strokeThicknessVariationAmplitude = .07;
    @XmlElement(name = "StrokeThicknessVariationWavelength")
    private double strokeThicknessVariationWavelength = 1. / .05;

    /**
     * 
     */
    public StrokeTextureExpressiveRendering() {
    }

    /**
     * @return the paperTextureFilename
     */
    public String getPaperTextureFilename() {
        return this.paperTextureFilename;
    }

    /**
     * @param paperTextureFilename
     *            the paperTextureFilename to set
     */
    public void setPaperTextureFilename(String paperTextureFilename) {
        this.paperTextureFilename = paperTextureFilename;
    }

    /**
     * @return the brushTextureFilename
     */
    public String getBrushTextureFilename() {
        return this.brushTextureFilename;
    }

    /**
     * @param brushTextureFilename
     *            the brushTextureFilename to set
     */
    public void setBrushTextureFilename(String brushTextureFilename) {
        this.brushTextureFilename = brushTextureFilename;
    }

    /**
     * @return the brushStartLength
     */
    public int getBrushStartLength() {
        return this.brushStartLength;
    }

    /**
     * @param brushStartLength
     *            the brushStartLength to set
     */
    public void setBrushStartLength(int brushStartLength) {
        this.brushStartLength = brushStartLength;
    }

    /**
     * @return the brushEndLength
     */
    public int getBrushEndLength() {
        return this.brushEndLength;
    }

    /**
     * @param brushEndLength
     *            the brushEndLength to set
     */
    public void setBrushEndLength(int brushEndLength) {
        this.brushEndLength = brushEndLength;
    }

    /**
     * @return the sampleSize
     */
    public double getSampleSize() {
        return this.sampleSize;
    }

    /**
     * @param sampleSize
     *            the sampleSize to set
     */
    public void setSampleSize(double sampleSize) {
        this.sampleSize = sampleSize;
    }

    /**
     * @return the minAngle
     */
    public double getMinAngle() {
        return this.minAngle;
    }

    /**
     * @param minAngle
     *            the minAngle to set
     */
    public void setMinAngle(double minAngle) {
        this.minAngle = minAngle;
    }

    /**
     * @return the brushSize
     */
    public double getBrushSize() {
        return this.brushSize;
    }

    /**
     * @param brushSize
     *            the brushSize to set
     */
    public void setBrushSize(double brushSize) {
        this.brushSize = brushSize;
    }

    /**
     * @return the paperScaleFactor
     */
    public double getPaperScaleFactor() {
        return this.paperScaleFactor;
    }

    /**
     * @param paperScaleFactor
     *            the paperScaleFactor to set
     */
    public void setPaperScaleFactor(double paperScaleFactor) {
        this.paperScaleFactor = paperScaleFactor;
    }

    /**
     * @return the paperDensity
     */
    public double getPaperDensity() {
        return this.paperDensity;
    }

    /**
     * @param paperDensity
     *            the paperDensity to set
     */
    public void setPaperDensity(double paperDensity) {
        this.paperDensity = paperDensity;
    }

    /**
     * @return the brushDensity
     */
    public double getBrushDensity() {
        return this.brushDensity;
    }

    /**
     * @param brushDensity
     *            the brushDensity to set
     */
    public void setBrushDensity(double brushDensity) {
        this.brushDensity = brushDensity;
    }

    /**
     * @return the strokePressure
     */
    public double getStrokePressure() {
        return this.strokePressure;
    }

    /**
     * @param strokePressure
     *            the strokePressure to set
     */
    public void setStrokePressure(double strokePressure) {
        this.strokePressure = strokePressure;
    }

    /**
     * @return the sharpness
     */
    public double getSharpness() {
        return this.sharpness;
    }

    /**
     * @param sharpness
     *            the sharpness to set
     */
    public void setSharpness(double sharpness) {
        this.sharpness = sharpness;
    }

    /**
     * @return the strokePressureVariationAmplitude
     */
    public double getStrokePressureVariationAmplitude() {
        return this.strokePressureVariationAmplitude;
    }

    /**
     * @param strokePressureVariationAmplitude
     *            the strokePressureVariationAmplitude to set
     */
    public void setStrokePressureVariationAmplitude(
            double strokePressureVariationAmplitude) {
        this.strokePressureVariationAmplitude = strokePressureVariationAmplitude;
    }

    /**
     * @return the strokePressureVariationWavelength
     */
    public double getStrokePressureVariationWavelength() {
        return this.strokePressureVariationWavelength;
    }

    /**
     * @param strokePressureVariationWavelength
     *            the strokePressureVariationWavelength to set
     */
    public void setStrokePressureVariationWavelength(
            double strokePressureVariationWavelength) {
        this.strokePressureVariationWavelength = strokePressureVariationWavelength;
    }

    /**
     * @return the strokeShiftVariationAmplitude
     */
    public double getStrokeShiftVariationAmplitude() {
        return this.strokeShiftVariationAmplitude;
    }

    /**
     * @param strokeShiftVariationAmplitude
     *            the strokeShiftVariationAmplitude to set
     */
    public void setStrokeShiftVariationAmplitude(
            double strokeShiftVariationAmplitude) {
        this.strokeShiftVariationAmplitude = strokeShiftVariationAmplitude;
    }

    /**
     * @return the strokeShiftVariationWavelength
     */
    public double getStrokeShiftVariationWavelength() {
        return this.strokeShiftVariationWavelength;
    }

    /**
     * @param strokeShiftVariationWavelength
     *            the strokeShiftVariationWavelength to set
     */
    public void setStrokeShiftVariationWavelength(
            double strokeShiftVariationWavelength) {
        this.strokeShiftVariationWavelength = strokeShiftVariationWavelength;
    }

    /**
     * @return the strokeThicknessVariationAmplitude
     */
    public double getStrokeThicknessVariationAmplitude() {
        return this.strokeThicknessVariationAmplitude;
    }

    /**
     * @param strokeThicknessVariationAmplitude
     *            the strokeThicknessVariationAmplitude to set
     */
    public void setStrokeThicknessVariationAmplitude(
            double strokeThicknessVariationAmplitude) {
        this.strokeThicknessVariationAmplitude = strokeThicknessVariationAmplitude;
    }

    /**
     * @return the strokeThicknessVariationWavelength
     */
    public double getStrokeThicknessVariationWavelength() {
        return this.strokeThicknessVariationWavelength;
    }

    /**
     * @param strokeThicknessVariationWavelength
     *            the strokeThicknessVariationWavelength to set
     */
    public void setStrokeThicknessVariationWavelength(
            double strokeThicknessVariationWavelength) {
        this.strokeThicknessVariationWavelength = strokeThicknessVariationWavelength;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        long temp;
        temp = Double.doubleToLongBits(this.brushDensity);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime * result + this.brushEndLength;
        temp = Double.doubleToLongBits(this.brushSize);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime * result + this.brushStartLength;
        result = prime
                * result
                + ((this.brushTextureFilename == null) ? 0
                        : this.brushTextureFilename.hashCode());
        temp = Double.doubleToLongBits(this.minAngle);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(this.paperDensity);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(this.paperScaleFactor);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime
                * result
                + ((this.paperTextureFilename == null) ? 0
                        : this.paperTextureFilename.hashCode());
        temp = Double.doubleToLongBits(this.sampleSize);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(this.sharpness);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(this.strokePressure);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(this.strokePressureVariationAmplitude);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(this.strokePressureVariationWavelength);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(this.strokeShiftVariationAmplitude);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(this.strokeShiftVariationWavelength);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(this.strokeThicknessVariationAmplitude);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(this.strokeThicknessVariationWavelength);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        StrokeTextureExpressiveRendering other = (StrokeTextureExpressiveRendering) obj;
        if (Double.doubleToLongBits(this.brushDensity) != Double
                .doubleToLongBits(other.brushDensity)) {
            return false;
        }
        if (this.brushEndLength != other.brushEndLength) {
            return false;
        }
        if (Double.doubleToLongBits(this.brushSize) != Double
                .doubleToLongBits(other.brushSize)) {
            return false;
        }
        if (this.brushStartLength != other.brushStartLength) {
            return false;
        }
        if (this.brushTextureFilename == null) {
            if (other.brushTextureFilename != null) {
                return false;
            }
        } else if (!this.brushTextureFilename
                .equals(other.brushTextureFilename)) {
            return false;
        }
        if (Double.doubleToLongBits(this.minAngle) != Double
                .doubleToLongBits(other.minAngle)) {
            return false;
        }
        if (Double.doubleToLongBits(this.paperDensity) != Double
                .doubleToLongBits(other.paperDensity)) {
            return false;
        }
        if (Double.doubleToLongBits(this.paperScaleFactor) != Double
                .doubleToLongBits(other.paperScaleFactor)) {
            return false;
        }
        if (this.paperTextureFilename == null) {
            if (other.paperTextureFilename != null) {
                return false;
            }
        } else if (!this.paperTextureFilename
                .equals(other.paperTextureFilename)) {
            return false;
        }
        if (Double.doubleToLongBits(this.sampleSize) != Double
                .doubleToLongBits(other.sampleSize)) {
            return false;
        }
        if (Double.doubleToLongBits(this.sharpness) != Double
                .doubleToLongBits(other.sharpness)) {
            return false;
        }
        if (Double.doubleToLongBits(this.strokePressure) != Double
                .doubleToLongBits(other.strokePressure)) {
            return false;
        }
        if (Double.doubleToLongBits(this.strokePressureVariationAmplitude) != Double
                .doubleToLongBits(other.strokePressureVariationAmplitude)) {
            return false;
        }
        if (Double.doubleToLongBits(this.strokePressureVariationWavelength) != Double
                .doubleToLongBits(other.strokePressureVariationWavelength)) {
            return false;
        }
        if (Double.doubleToLongBits(this.strokeShiftVariationAmplitude) != Double
                .doubleToLongBits(other.strokeShiftVariationAmplitude)) {
            return false;
        }
        if (Double.doubleToLongBits(this.strokeShiftVariationWavelength) != Double
                .doubleToLongBits(other.strokeShiftVariationWavelength)) {
            return false;
        }
        if (Double.doubleToLongBits(this.strokeThicknessVariationAmplitude) != Double
                .doubleToLongBits(other.strokeThicknessVariationAmplitude)) {
            return false;
        }
        if (Double.doubleToLongBits(this.strokeThicknessVariationWavelength) != Double
                .doubleToLongBits(other.strokeThicknessVariationWavelength)) {
            return false;
        }
        return true;
    }

}
