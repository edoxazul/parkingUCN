/*
 * Copyright (c) 2020. This is only for education, do not use in production. GG
 *
 * Alvaro Lucas Castillo Calabacero
 * alvarolucascc96@gmail.com
 */

package cl.ucn.disc.pdis.scrapper.bd;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "Academicos")
public class Academic {

  //id of table academic
  @DatabaseField(id = true)
  private int id;

  // name of academic
  @DatabaseField
  private String name;

  //"cargo" of academic
  @DatabaseField
  private String position;

  //unit of academic
  @DatabaseField
  private String unit;

  // email of academic
  @DatabaseField
  private String email;

  //phone of academic
  @DatabaseField
  private String phone;

  //office of academic
  @DatabaseField
  private String office;

  //address of academic
  @DatabaseField
  private String address;

  /*
  * Constructor void for ormlite
  */
  public Academic() {
    //void for ORMLITE
  }

  /**
   * Constructor of Academic.
   * @param id of academic
   * @param name of academic
   * @param position of academic
   * @param unit of academic
   * @param email of academic
   * @param phone of academic
   * @param office of academic
   * @param address of academic
   */
  public Academic(int id, String name, String position, String unit, String email, String phone,
                  String office, String address) {
    this.id = id;
    this.name = name;
    this.position = position;
    this.unit = unit;
    this.email = email;
    this.phone = phone;
    this.office = office;
    this.address = address;
  }

  /**
   * to String.
   * @return resumen of Academic
   */
  @Override
  public String toString() {
    return "Academic{"
        + "id=" + id
        + ", name='" + name + '\''
        + ", position='" + position + '\''
        + ", unit='" + unit + '\''
        + ", email='" + email + '\''
        + ", phone='" + phone + '\''
        + ", office='" + office + '\''
        + ", address='" + address + '\''
        + '}';
  }
}
