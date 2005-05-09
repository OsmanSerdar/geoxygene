/*
 * This file is part of the GeOxygene project source files. 
 * 
 * GeOxygene aims at providing an open framework compliant with OGC/ISO specifications for 
 * the development and deployment of geographic (GIS) applications. It is a open source 
 * contribution of the COGIT laboratory at the Institut Géographique National (the French 
 * National Mapping Agency).
 * 
 * See: http://oxygene-project.sourceforge.net 
 *  
 * Copyright (C) 2005 Institut Géographique National
 *
 * This library is free software; you can redistribute it and/or modify it under the terms
 * of the GNU Lesser General Public License as published by the Free Software Foundation; 
 * either version 2.1 of the License, or any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY 
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A 
 * PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along with 
 * this library (see file LICENSE if present); if not, write to the Free Software 
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 *  
 */

/* Generated By:JavaCC: Do not edit this line. WktAwtConstants.java */
package fr.ign.cogit.geoxygene.util.conversion;

public interface WktAwtConstants {

  int EOF = 0;
  int CONSTANT = 1;
  int FLOAT = 2;
  int INTEGER = 3;
  int DIGIT = 4;
  int POINT = 5;
  int EOL = 6;

  int DEFAULT = 0;

  String[] tokenImage = {
    "<EOF>",
    "<CONSTANT>",
    "<FLOAT>",
    "<INTEGER>",
    "<DIGIT>",
    "<POINT>",
    "\"\\n\"",
    "\"\\t\"",
    "\" \"",
    "\"(\"",
    "\")\"",
    "\"EMPTY\"",
    "\",\"",
    "\"POINT\"",
    "\"LINESTRING\"",
    "\"MULTIPOINT\"",
    "\"MULTILINESTRING\"",
    "\"POLYGON\"",
    "\"MULTIPOLYGON\"",
    "\"GEOMETRYCOLLECTION\"",
  };

}
