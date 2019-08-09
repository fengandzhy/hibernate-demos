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
	 * 	通过annotation来映射hibernate实体的,基于annotation的hibernate主键标识为@Id,其生成规则由@GeneratedValue设定的.	
	 * 	TABLE：使用一个特定的数据库表格来保存主键。
	 * 	SEQUENCE：根据底层数据库的序列来生成主键，条件是数据库支持序列。
	 * 	IDENTITY：主键由数据库自动生成（主要是自动增长型）
	 * 	AUTO：主键由程序控制。 它是默认的配置。如果不指定主键生成策略，默认为AUTO。
	 * **************************************************************************
	 * 	hibernate主键策略生成器
	 * 	native: 对于 oracle 采用 Sequence 方式，对于MySQL 和 SQL Server 采用identity（自增主键生成机制），native就是将主键的生成工作交由数据库完成，hibernate不管
	 * 	uuid: 采用128位的uuid算法生成主键，uuid被编码为一个32位16进制数字的字符串。占用空间大（字符串类型）。
	 * 	assigned: 在插入数据的时候主键由程序处理（即程序员手动指定），这是 <generator>元素没有指定时的默认生成策略。等同于JPA中的AUTO。
	 * 	identity: 使用SQL Server 和 MySQL 的自增字段，这个方法不能放到 Oracle 中，Oracle 不支持自增字段，要设定sequence（MySQL 和 SQL Server 中很常用）。 等同于JPA中的INDENTITY。
	 * 	increment: 插入数据的时候hibernate会给主键添加一个自增的主键，但是一个hibernate实例就维护一个计数器，所以在多个实例运行的时候不能使用这个方法。
	 * **********************************************************************************	
	 * 	hibernate提供了多种生成器供选择,基于Annotation的方式通过@GenericGenerator实现
	 * 	比如说，JPA标准用法
	 * 	@Id 
	 * 	@GeneratedValue(GenerationType.AUTO)  
	 * 	就可以用hibernate特有以下用法来代替:
	 * 	@Id
	 * 	@GeneratedValue(generator = "paymentableGenerator")
	 * 	@GenericGenerator(name = "paymentableGenerator", strategy = "assigned")  
	 *  在本例中采用
	 *  @Id
	 *  @GeneratedValue(generator = "paymentableGenerator")
	 *  @GenericGenerator(name = "paymentableGenerator", strategy = "native")
	 *  相当于JPA中使用
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
