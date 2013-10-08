package fr.esiea.windmeal.model.security;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

/**
 * Copyright (c) 2013 ESIEA M. Labusquiere D. Déïs
 * <p/>
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 * <p/>
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * <p/>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
public enum Profile {

	ADMIN(Collections.unmodifiableList(Arrays.asList(Role.ROLE_ADMIN, Role.ROLE_USER))),
	PROVIDER(Collections.unmodifiableList(Arrays.asList(Role.ROLE_PROVIDER,Role.ROLE_ADMIN,Role.ROLE_USER))),
    USER(Collections.unmodifiableList(Arrays.asList(Role.ROLE_USER)));

	private Profile(Collection<String> roleList) {
		this.roleList = roleList;
	}

	private final Collection<String> roleList;

	public Collection<String> getRoleList() {
		return roleList;
	}
}