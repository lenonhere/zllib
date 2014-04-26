
package com.ws.basedata.contshippment;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="comCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="indCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="contShippment" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "comCode",
    "indCode",
    "contShippment"
})
@XmlRootElement(name = "transContShippment")
public class TransContShippment {

    @XmlElement(required = true, nillable = true)
    protected String comCode;
    @XmlElement(required = true, nillable = true)
    protected String indCode;
    @XmlElement(required = true, nillable = true)
    protected String contShippment;

    /**
     * Gets the value of the comCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getComCode() {
        return comCode;
    }

    /**
     * Sets the value of the comCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setComCode(String value) {
        this.comCode = value;
    }

    /**
     * Gets the value of the indCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIndCode() {
        return indCode;
    }

    /**
     * Sets the value of the indCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIndCode(String value) {
        this.indCode = value;
    }

    /**
     * Gets the value of the contShippment property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContShippment() {
        return contShippment;
    }

    /**
     * Sets the value of the contShippment property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContShippment(String value) {
        this.contShippment = value;
    }

}
