package com.dao.interfaces;

import com.facade.*;
/* TODO: why we need this interface? we can write the 'login()' in the Facade classes.
 * WHY DO WE NEED THIS Class?
 */

public interface CouponClientFacade {

	public boolean login (String userName, long password);
}
