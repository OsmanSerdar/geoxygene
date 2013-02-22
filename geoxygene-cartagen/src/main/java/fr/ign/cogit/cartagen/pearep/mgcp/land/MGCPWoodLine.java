package fr.ign.cogit.cartagen.pearep.mgcp.land;

import java.util.HashMap;

import fr.ign.cogit.cartagen.core.genericschema.land.IWoodLine;
import fr.ign.cogit.cartagen.pearep.mgcp.MGCPFeature;
import fr.ign.cogit.cartagen.pearep.vmap.PeaRepDbType;
import fr.ign.cogit.geoxygene.api.feature.IFeature;
import fr.ign.cogit.geoxygene.api.spatial.coordgeom.ILineString;
import fr.ign.cogit.geoxygene.schemageo.api.occSol.ElementIsole;
import fr.ign.cogit.geoxygene.schemageo.impl.occSol.ElementIsoleImpl;

public class MGCPWoodLine extends MGCPFeature implements IWoodLine {

  private ElementIsole geoxObj;

  private String upd_date, upd_info, cpyrt_note, src_info, txt, nfi, nfn,
      tier_note, nam, dmb, src_date, dmt, ace, ale, pfh;
  private long acc, ace_eval, ale_eval, uid, upd_name, src_name, zval_type,
      iss, vsp, dir, tre;

  /**
   * @param type
   */
  public MGCPWoodLine(ILineString line, HashMap<String, Object> attributes,
      PeaRepDbType type) {
    super();
    this.setGeoxObj(new ElementIsoleImpl());
    this.getGeoxObj().setGeom(line);
    this.setInitialGeom(line);
    this.setEliminated(false);

    // attributes present in Mgcp++
    this.acc = (Long) attributes.get("acc");
    this.ace_eval = (Long) attributes.get("ace_eval");
    this.ale_eval = (Long) attributes.get("ale_eval");
    if (attributes.containsKey("uid"))
      this.uid = (Long) attributes.get("uid");
    this.src_name = (Long) attributes.get("src_name");
    this.zval_type = (Long) attributes.get("zval_type");
    this.dmb = (String) attributes.get("dmb");
    this.iss = (Long) attributes.get("iss");
    this.dmt = (String) attributes.get("dmt");
    this.pfh = (String) attributes.get("pfh");
    this.vsp = (Long) attributes.get("vsp");
    this.tre = (Long) attributes.get("tre");
    this.upd_date = (String) attributes.get("upd_date");
    this.upd_info = (String) attributes.get("upd_info");
    this.cpyrt_note = (String) attributes.get("cpyrt_note");
    this.upd_name = (Long) attributes.get("upd_name");
    this.src_info = (String) attributes.get("src_info");
    this.txt = (String) attributes.get("txt");
    this.nfi = (String) attributes.get("nfi");
    this.nfn = (String) attributes.get("nfn");
    this.tier_note = (String) attributes.get("tier_note");
    this.nam = (String) attributes.get("nam");
    this.src_date = (String) attributes.get("src_date");
    this.ace = (String) attributes.get("ace");
    this.ale = (String) attributes.get("ale");
  }

  private void setGeoxObj(ElementIsole feature) {
    this.geoxObj = feature;
  }

  @Override
  public ILineString getGeom() {
    return (ILineString) super.getGeom();
  }

  @Override
  public IFeature getGeoxObj() {
    return geoxObj;
  }

  public String getUpd_date() {
    return upd_date;
  }

  public void setUpd_date(String upd_date) {
    this.upd_date = upd_date;
  }

  public String getUpd_info() {
    return upd_info;
  }

  public void setUpd_info(String upd_info) {
    this.upd_info = upd_info;
  }

  public String getCpyrt_note() {
    return cpyrt_note;
  }

  public void setCpyrt_note(String cpyrt_note) {
    this.cpyrt_note = cpyrt_note;
  }

  public String getSrc_info() {
    return src_info;
  }

  public void setSrc_info(String src_info) {
    this.src_info = src_info;
  }

  public String getTxt() {
    return txt;
  }

  public void setTxt(String txt) {
    this.txt = txt;
  }

  public String getNfi() {
    return nfi;
  }

  public void setNfi(String nfi) {
    this.nfi = nfi;
  }

  public String getNfn() {
    return nfn;
  }

  public void setNfn(String nfn) {
    this.nfn = nfn;
  }

  public String getTier_note() {
    return tier_note;
  }

  public void setTier_note(String tier_note) {
    this.tier_note = tier_note;
  }

  public String getNam() {
    return nam;
  }

  public void setNam(String nam) {
    this.nam = nam;
  }

  public String getDmb() {
    return dmb;
  }

  public void setDmb(String dmb) {
    this.dmb = dmb;
  }

  public String getSrc_date() {
    return src_date;
  }

  public void setSrc_date(String src_date) {
    this.src_date = src_date;
  }

  public String getDmt() {
    return dmt;
  }

  public void setDmt(String dmt) {
    this.dmt = dmt;
  }

  public String getAce() {
    return ace;
  }

  public void setAce(String ace) {
    this.ace = ace;
  }

  public String getAle() {
    return ale;
  }

  public void setAle(String ale) {
    this.ale = ale;
  }

  public String getPfh() {
    return pfh;
  }

  public void setPfh(String pfh) {
    this.pfh = pfh;
  }

  public long getAcc() {
    return acc;
  }

  public void setAcc(long acc) {
    this.acc = acc;
  }

  public long getAce_eval() {
    return ace_eval;
  }

  public void setAce_eval(long ace_eval) {
    this.ace_eval = ace_eval;
  }

  public long getAle_eval() {
    return ale_eval;
  }

  public void setAle_eval(long ale_eval) {
    this.ale_eval = ale_eval;
  }

  public long getUid() {
    return uid;
  }

  public void setUid(long uid) {
    this.uid = uid;
  }

  public long getUpd_name() {
    return upd_name;
  }

  public void setUpd_name(long upd_name) {
    this.upd_name = upd_name;
  }

  public long getSrc_name() {
    return src_name;
  }

  public void setSrc_name(long src_name) {
    this.src_name = src_name;
  }

  public long getZval_type() {
    return zval_type;
  }

  public void setZval_type(long zval_type) {
    this.zval_type = zval_type;
  }

  public long getIss() {
    return iss;
  }

  public void setIss(long iss) {
    this.iss = iss;
  }

  public long getVsp() {
    return vsp;
  }

  public void setVsp(long vsp) {
    this.vsp = vsp;
  }

  public long getDir() {
    return dir;
  }

  public void setDir(long dir) {
    this.dir = dir;
  }

  public long getTre() {
    return tre;
  }

  public void setTre(long tre) {
    this.tre = tre;
  }

}
