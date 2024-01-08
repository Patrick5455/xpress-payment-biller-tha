
package com.xpresspayment.takehometest.commons.enumconstants;
import java.util.Arrays;
import java.util.List;

public enum Role {

    ROLE_BUSINESS_PRINCIPAL {
        @Override
        public Authority getAuthority() {
            return Authority.ACCOUNT_ACCESS;
        }
    },

    ROLE_BUSINESS_MEMBER {
        @Override
        public Authority getAuthority() {
            return Authority.SUB_ACCOUNT_ACCESS;
        }
    },

    ROLE_RETAIL_INVESTOR {
        @Override
        public Authority getAuthority() {
            return Authority.ACCOUNT_ACCESS;
        }
    },

    ROLE_CORPORATE_INVESTOR_PRINCIPAL {
        @Override
        public Authority getAuthority() {
            return Authority.ACCOUNT_ACCESS;
        }
    },

    ROLE_CORPORATE_INVESTOR_MEMBER {
        @Override
        public Authority getAuthority() {
            return Authority.SUB_ACCOUNT_ACCESS;
        }
    },

    ROLE_SUPER_ADMIN {
        @Override
        public Authority getAuthority() {
            return Authority.SUPER_ADMIN_ACCESS;
        }
    },

    ROLE_ADMIN {
        @Override
        public Authority getAuthority() {
            return Authority.ADMIN_ACCESS;
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
        ACCOUNT_ACCESS {
            @Override
            List<AuthorityPrivileges> getPrivileges() {
                return null;
            }
        },
        SUB_ACCOUNT_ACCESS {
            @Override
            List<AuthorityPrivileges> getPrivileges() {
                return null;
            }
        };

        abstract List<AuthorityPrivileges> getPrivileges();
    }


    /**
     * @apiNote Privlieges are actions that can be taken based on the type of authority
     * Privileges for authorities
     */
    public enum AuthorityPrivileges {
        //TODO enumerate all the privileges an auth can have
    }


}
