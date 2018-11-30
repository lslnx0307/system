package com.jsjf.model.system;

import java.io.Serializable;

public class SysUserRoleVo  implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database column sys_user_role.GRANT_KY
     *
     * @abatorgenerated Thu Aug 08 17:25:21 GMT+08:00 2013
     */
    private Long grantKy;

    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database column sys_user_role.USER_KY
     *
     * @abatorgenerated Thu Aug 08 17:25:21 GMT+08:00 2013
     */
    private Long userKy;

    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database column sys_user_role.ROLE_KY
     *
     * @abatorgenerated Thu Aug 08 17:25:21 GMT+08:00 2013
     */
    private Long roleKy;

    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database column sys_user_role.STATUS
     *
     * @abatorgenerated Thu Aug 08 17:25:21 GMT+08:00 2013
     */
    private Short status;
   
	/**
     * This method was generated by Abator for iBATIS.
     * This method returns the value of the database column sys_user_role.GRANT_KY
     *
     * @return the value of sys_user_role.GRANT_KY
     *
     * @abatorgenerated Thu Aug 08 17:25:21 GMT+08:00 2013
     */
    public Long getGrantKy() {
        return grantKy;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method sets the value of the database column sys_user_role.GRANT_KY
     *
     * @param grantKy the value for sys_user_role.GRANT_KY
     *
     * @abatorgenerated Thu Aug 08 17:25:21 GMT+08:00 2013
     */
    public void setGrantKy(Long grantKy) {
        this.grantKy = grantKy;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method returns the value of the database column sys_user_role.USER_KY
     *
     * @return the value of sys_user_role.USER_KY
     *
     * @abatorgenerated Thu Aug 08 17:25:21 GMT+08:00 2013
     */
    public Long getUserKy() {
        return userKy;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method sets the value of the database column sys_user_role.USER_KY
     *
     * @param userKy the value for sys_user_role.USER_KY
     *
     * @abatorgenerated Thu Aug 08 17:25:21 GMT+08:00 2013
     */
    public void setUserKy(Long userKy) {
        this.userKy = userKy;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method returns the value of the database column sys_user_role.ROLE_KY
     *
     * @return the value of sys_user_role.ROLE_KY
     *
     * @abatorgenerated Thu Aug 08 17:25:21 GMT+08:00 2013
     */
    public Long getRoleKy() {
        return roleKy;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method sets the value of the database column sys_user_role.ROLE_KY
     *
     * @param roleKy the value for sys_user_role.ROLE_KY
     *
     * @abatorgenerated Thu Aug 08 17:25:21 GMT+08:00 2013
     */
    public void setRoleKy(Long roleKy) {
        this.roleKy = roleKy;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method returns the value of the database column sys_user_role.STATUS
     *
     * @return the value of sys_user_role.STATUS
     *
     * @abatorgenerated Thu Aug 08 17:25:21 GMT+08:00 2013
     */
    public Short getStatus() {
        return status;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method sets the value of the database column sys_user_role.STATUS
     *
     * @param status the value for sys_user_role.STATUS
     *
     * @abatorgenerated Thu Aug 08 17:25:21 GMT+08:00 2013
     */
    public void setStatus(Short status) {
        this.status = status;
    }
}