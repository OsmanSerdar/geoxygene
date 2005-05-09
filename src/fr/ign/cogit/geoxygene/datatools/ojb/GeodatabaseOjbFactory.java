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

package fr.ign.cogit.geoxygene.datatools.ojb;

import oracle.jdbc.driver.OracleConnection;

import org.postgresql.PGConnection;

import fr.ign.cogit.geoxygene.datatools.Geodatabase;

/**
 * Constructeur de GeodatabaseOjb (Oracle ou Postgis) a partir d'un alias de connection
 * defini dans le fichier de configuration repository_database.xml.
 * 
 * @author Thierry Badard & Arnaud Braun
 * @version 1.0
 *
 */
public class GeodatabaseOjbFactory {
	
	/** Constructeur de GeodatabaseOjb (Oracle ou Postgis) a partir d'un alias de connection
	 * defini dans le fichier de configuration repository_database.xml.*/
	public static Geodatabase newInstance(String jcdAlias) {
		GeodatabaseOjb ojb = new GeodatabaseOjb(jcdAlias);	
		
		 if (ojb._conn instanceof PGConnection) 
			 return new GeodatabaseOjbPostgis(ojb);
		 
		 else if (ojb._conn instanceof OracleConnection) 
			 return new GeodatabaseOjbOracle(ojb);
		 
		 else {
			 System.out.println("### Cas non traite : "+ojb._conn.getClass().getName());
			 System.exit(0);
			 return null;
		 }
   		
	}	
   
	/** Constructeur de GeodatabaseOjb (Oracle ou Postgis) a partir de la connection par defaut
	 * definie dans le fichier de configuration repository_database.xml.*/  
	public static Geodatabase newInstance() {
		 return GeodatabaseOjbFactory.newInstance(null);
	}
   
}
