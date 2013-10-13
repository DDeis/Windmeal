package fr.esiea.windmeal.model;

import com.fasterxml.jackson.annotation.JsonView;
import fr.esiea.windmeal.model.enumeration.Tag;

import java.util.Set;

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
public class FoodProvider extends Model {

    @JsonView(Views.LightView.class)
	private String name;
    @JsonView(Views.FullView.class)
	private Address address;
    @JsonView(Views.FullView.class)
    private String phone;
    @JsonView(Views.FullView.class)
	private String email;
    @JsonView(Views.FullView.class)
	private String description;
    @JsonView(Views.FullView.class)
	private String menuId;
    @JsonView(Views.FullView.class)
	private String ownerId;
    @JsonView(Views.FullView.class)
	private Set<Comment> comments;
    @JsonView(Views.FullView.class)
    private Set<Tag> tags;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	public Set<Tag> getTags() {
		return tags;
	}

	public void setTags(Set<Tag> tags) {
		this.tags = tags;
	}

	public Set<Comment> getComments() {
		return comments;
	}

	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof FoodProvider)) return false;

		FoodProvider that = (FoodProvider) o;

		if (address != null ? !address.equals(that.address) : that.address != null) return false;
		if (comments != null ? !comments.equals(that.comments) : that.comments != null) return false;
		if (description != null ? !description.equals(that.description) : that.description != null) return false;
		if (email != null ? !email.equals(that.email) : that.email != null) return false;
		if (menuId != null ? !menuId.equals(that.menuId) : that.menuId != null) return false;
		if (name != null ? !name.equals(that.name) : that.name != null) return false;
		if (ownerId != null ? !ownerId.equals(that.ownerId) : that.ownerId != null) return false;
		if (phone != null ? !phone.equals(that.phone) : that.phone != null) return false;
		if (tags != null ? !tags.equals(that.tags) : that.tags != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = name != null ? name.hashCode() : 0;
		result = 31 * result + (address != null ? address.hashCode() : 0);
		result = 31 * result + (phone != null ? phone.hashCode() : 0);
		result = 31 * result + (email != null ? email.hashCode() : 0);
		result = 31 * result + (description != null ? description.hashCode() : 0);
		result = 31 * result + (menuId != null ? menuId.hashCode() : 0);
		result = 31 * result + (ownerId != null ? ownerId.hashCode() : 0);
		result = 31 * result + (comments != null ? comments.hashCode() : 0);
		result = 31 * result + (tags != null ? tags.hashCode() : 0);
		return result;
	}

    @Override
    public String toString() {
        return "FoodProvider{" +
                "name='" + name + '\'' +
                ", address=" + address +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", description='" + description + '\'' +
                ", menuId='" + menuId + '\'' +
                ", ownerId='" + ownerId + '\'' +
                ", comments=" + comments +
                ", tags=" + tags +
                "} " + super.toString();
    }

    public static class Views {
        public static class LightView {   }
        public static class FullView extends LightView {    }
    }
}
