/*
 * Copyright (c) 2020. This is only for education, do not use in production. GG
 *
 * Alvaro Lucas Castillo Calabacero
 * alvarolucascc96@gmail.com
 */

package cl.ucn.disc.pdis.scrapper.BD;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "Academicos")
public class Academic {

    /**
     * Field of database
     */
    @DatabaseField(id = true)
    private int id;
    @DatabaseField
    private String name;
    @DatabaseField
    private String position;
    @DatabaseField
    private String unit;
    @DatabaseField
    private String email;
    @DatabaseField
    private String phone;
    @DatabaseField
    private String office;
    @DatabaseField
    private String address;

    /**
     * Ormlite constructor
     */
    public Academic() {
        //void for ORMLITE
    }

    /**
     * Contructor for field
     *
     * @param id
     * @param name
     * @param position
     * @param unit
     * @param email
     * @param phone
     * @param office
     * @param address
     */
    public Academic(int id, String name, String position, String unit, String email, String phone, String office, String address) {
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
     * to String
     *
     * @return resumen of Academic
     */
    @Override
    public String toString() {
        return "Academic{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", position='" + position + '\'' +
                ", unit='" + unit + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", office='" + office + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
