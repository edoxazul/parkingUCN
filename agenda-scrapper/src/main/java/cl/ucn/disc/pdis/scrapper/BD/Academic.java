/*
 * MIT License
 *
 * Copyright (c) 2020 Eduardo Alvarez , Alvaro Castillo , Ignacio Fuenzalida
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package cl.ucn.disc.pdis.scrapper.bd;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Academic class Implementation.
 * @author Ignacio Fuenzalida
 * @author Alvaro Castillo
 * @author Eduardo Alvarez
 */
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
