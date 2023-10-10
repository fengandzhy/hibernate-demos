package org.zhouhy.hibernate.many2many.models;

import org.hibernate.Transaction;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.junit.Test;

public class Many2ManyTestForSave extends Many2ManyTest{
    
    /**
     * 1.当两边都设置了 @JoinTable 表示两边都要参与维护外键，就是说两边都要去更新中间表t1_user_role, 
     * 所以当两边都设置了关联关系后, 它就会出错. 因此只设置一边的关联关系.
     * 
     *     @ManyToMany(targetEntity=Role.class)
     *     @JoinTable(name="t1_user_role",  // t1_user_role 中间表的表名          
     *             joinColumns = @JoinColumn(referencedColumnName="Id",name="user_id"),// Id 是 User对应表 主键的列名, user_id 中间表中关联User的列名.
     *             inverseJoinColumns = @JoinColumn(referencedColumnName="Id",name="role_id")) // Id 是Role对应表 主键列的名称, role_id 是中间表中关联Role的列名.
     *     private Set<Role> roles = new HashSet<>();
     *     
     *     @ManyToMany(targetEntity=User.class)
     *     @JoinTable(name="t1_user_role", // t1_user_role 中间表的表名
     *             joinColumns = @JoinColumn(referencedColumnName="Id",name="role_id"), // Id 是Role 对应表的主键列的名称, role_id 是中间表中关联Role的列名.
     *             inverseJoinColumns = @JoinColumn(referencedColumnName="Id",name="user_id"))// Id 是 User对应表 主键的列名, user_id 中间表中关联User的列名.     *  
     *     private Set<User> users = new HashSet<>();
     * 
     * */    
    @Test
    public void testSaveForBothInverse(){
        Transaction transaction = session.beginTransaction();
        Role r1 = new Role();
        r1.setName("a1");
        
        User u1 = new User();
        u1.setUsername("b1");
        u1.setPassword("1");
        
        r1.getUsers().add(u1);
        // u1.getRoles().add(r1); // 由于两边都是 @JoinTable 所以只能设置单边的关系,这里只设置一端关联
        
        session.save(u1);
        session.save(r1); // 此时cascade = none 所以必须手动保存r1, 否则会报错
        
        if (transaction.getStatus().equals(TransactionStatus.ACTIVE)){
            transaction.commit();
        }
    }

    /**
     * 如果在Role中设置了mappedBy 表示Role 不再维护 t1_user_role,  所以设置role的关联关系r1.getUsers().add(u1); 不会再对t1_user_role 有作用
     * 
     *  一般情况下，我个人倾向于两边同时设置 @JoinTable, 保存的时候只设置一端的 关联关系. 配置关系如下所示, 此时 Role 就不在维护
     *  
     *     @ManyToMany(targetEntity=User.class,mappedBy="roles") // 这里的roles 表示在 User 类中的与Role关联的属性名.
     *     private Set<User> users = new HashSet<>();
     *     
     *     @ManyToMany(targetEntity=Role.class)
     *     @JoinTable(name="t1_user_role",  // t1_user_role 中间表的表名          
     *             joinColumns = @JoinColumn(referencedColumnName="Id",name="user_id"),// Id 是 User对应表 主键的列名, user_id 中间表中关联User的列名.
     *             inverseJoinColumns = @JoinColumn(referencedColumnName="Id",name="role_id")) // Id 是Role对应表 主键列的名称, role_id 是中间表中关联Role的列名.
     *     private Set<Role> roles = new HashSet<>();
     *     
     *     
     * */
    @Test
    public void testSaveForRoleInverse(){
        Transaction transaction = session.beginTransaction();
        Role r1 = new Role();
        r1.setName("a3");

        User u1 = new User();
        u1.setUsername("b3");
        u1.setPassword("1");

        r1.getUsers().add(u1);
        u1.getRoles().add(r1); 

        session.save(u1);
        session.save(r1);
        
        if (transaction.getStatus().equals(TransactionStatus.ACTIVE)){
            transaction.commit();
        }
    }
}
