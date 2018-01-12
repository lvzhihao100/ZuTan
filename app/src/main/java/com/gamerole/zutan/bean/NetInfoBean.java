package com.gamerole.zutan.bean;

import com.eqdd.library.bean.room.User;

import java.util.List;

/**
 * @author 吕志豪 .
 * @date 17-10-23  下午4:41.
 * Github :https://github.com/lvzhihao100
 * E-Mail：1030753080@qq.com
 * 简书 :http://www.jianshu.com/u/6e525b929aac
 */

public class NetInfoBean {
    Siblings elder;
    Siblings sibling;
    Siblings child;

    public Siblings getElder() {
        return elder;
    }

    public void setElder(Siblings elder) {
        this.elder = elder;
    }

    public Siblings getSibling() {
        return sibling;
    }

    public void setSibling(Siblings sibling) {
        this.sibling = sibling;
    }

    public Siblings getChild() {
        return child;
    }

    public void setChild(Siblings child) {
        this.child = child;
    }

   public class Siblings {
        int pos;
        List<User> users;

        public int getPos() {
            return pos;
        }

        public void setPos(int pos) {
            this.pos = pos;
        }

        public List<User> getUsers() {
            return users;
        }

        public void setUsers(List<User> users) {
            this.users = users;
        }
    }
}
