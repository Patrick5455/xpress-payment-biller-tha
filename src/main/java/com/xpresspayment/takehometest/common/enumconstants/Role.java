
package com.xpresspayment.takehometest.common.enumconstants;
import java.util.Arrays;
import java.util.List;

public enum Role {

    ROLE_ORDINARY_USER {
        @Override
        public Authority getAuthority() {
            return Authority.ORDINARY_USER_ACCESS;
        }
    },

    ROLE_ADMIN {
        @Override
        public Authority getAuthority() {
            return Authority.ADMIN_ACCESS;
        }
    },

    ROLE_SUPER_ADMIN {
        @Override
        public Authority getAuthority() {
            return Authority.SUPER_ADMIN_ACCESS;
        }
    };

    public abstract Authority getAuthority();

    /***
     * Authorities for each role type
     */

    public enum Authority {
        SUPER_ADMIN_ACCESS {
            @Override
            List<AuthorityPrivileges> getPrivileges() {
                return Arrays.asList(AuthorityPrivileges.values());
            }
        },
       ADMIN_ACCESS {
            @Override
            List<AuthorityPrivileges> getPrivileges() {
                return Arrays.asList(AuthorityPrivileges.values());
            }
        },
        ORDINARY_USER_ACCESS {
            @Override
            List<AuthorityPrivileges> getPrivileges() {
                return null;
            }
        };

        abstract List<AuthorityPrivileges> getPrivileges();
    }


    /**
     * @apiNote Privileges are actions that can be taken based on the type of authority
     * Privileges for authorities
     */
    public enum AuthorityPrivileges {
        //TODO enumerate all the privileges an auth can have
    }


}
