package com.citi.hibernate.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity(name = "Student")
@Table(name = "t_student")
public class Student implements Serializable{
	/**
	 * 	ͨ��annotation��ӳ��hibernateʵ���,����annotation��hibernate������ʶΪ@Id,�����ɹ�����@GeneratedValue�趨��.	
	 * 	TABLE��ʹ��һ���ض������ݿ���������������
	 * 	SEQUENCE�����ݵײ����ݿ���������������������������ݿ�֧�����С�
	 * 	IDENTITY�����������ݿ��Զ����ɣ���Ҫ���Զ������ͣ�
	 * 	AUTO�������ɳ�����ơ� ����Ĭ�ϵ����á������ָ���������ɲ��ԣ�Ĭ��ΪAUTO��
	 * **************************************************************************
	 * 	hibernate��������������
	 * 	native: ���� oracle ���� Sequence ��ʽ������MySQL �� SQL Server ����identity�������������ɻ��ƣ���native���ǽ����������ɹ����������ݿ���ɣ�hibernate����
	 * 	uuid: ����128λ��uuid�㷨����������uuid������Ϊһ��32λ16�������ֵ��ַ�����ռ�ÿռ���ַ������ͣ���
	 * 	assigned: �ڲ������ݵ�ʱ�������ɳ�����������Ա�ֶ�ָ���������� <generator>Ԫ��û��ָ��ʱ��Ĭ�����ɲ��ԡ���ͬ��JPA�е�AUTO��
	 * 	identity: ʹ��SQL Server �� MySQL �������ֶΣ�����������ܷŵ� Oracle �У�Oracle ��֧�������ֶΣ�Ҫ�趨sequence��MySQL �� SQL Server �кܳ��ã��� ��ͬ��JPA�е�INDENTITY��
	 * 	increment: �������ݵ�ʱ��hibernate����������һ������������������һ��hibernateʵ����ά��һ���������������ڶ��ʵ�����е�ʱ����ʹ�����������
	 * **********************************************************************************	
	 * 	hibernate�ṩ�˶�����������ѡ��,����Annotation�ķ�ʽͨ��@GenericGeneratorʵ��
	 * 	����˵��JPA��׼�÷�
	 * 	@Id 
	 * 	@GeneratedValue(GenerationType.AUTO)  
	 * 	�Ϳ�����hibernate���������÷�������:
	 * 	@Id
	 * 	@GeneratedValue(generator = "paymentableGenerator")
	 * 	@GenericGenerator(name = "paymentableGenerator", strategy = "assigned")  
	 *  �ڱ����в���
	 *  @Id
	 *  @GeneratedValue(generator = "paymentableGenerator")
	 *  @GenericGenerator(name = "paymentableGenerator", strategy = "native")
	 *  �൱��JPA��ʹ��
	 * 	@Id
	 * 	@GeneratedValue(strategy = GenerationType.IDENTITY)
	 * 
	 * 	
	 */
	private static final long serialVersionUID = 8954726584866114741L;
	
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "native")
	//@GeneratedValue(strategy = GenerationType.SEQUENCE)
	//@GeneratedValue(strategy = GenerationType.AUTO)
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	private int id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "sex")
	private String sex;
	
	@Column(name = "reg_date")
	private Date regDate;
	
	public Student() {
		super();
	}
	public Student(String name, String sex, Date regDate) {
		super();
		this.name = name;
		this.sex = sex;
		this.regDate = regDate;
	}	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public Date getRegDate() {
		return regDate;
	}
	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}
	@Override
	public String toString() {
		return "Student [id=" + id + ", name=" + name + ", sex=" + sex + ", regDate=" + regDate + "]";
	}
	
}
