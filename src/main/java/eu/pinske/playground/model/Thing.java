package eu.pinske.playground.model;

import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

import org.hibernate.engine.jdbc.BlobProxy;

@Entity
public class Thing {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	@Lob
	private Blob data;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean hasData() {
		return data != null;
	}

	public InputStream getData() {
		try {
			return data.getBinaryStream();
		} catch (SQLException e) {
			throw new IllegalStateException(e);
		}
	}

	public void setData(InputStream data) {
		if (data == null) {
			this.data = null;
		} else {
			this.data = BlobProxy.generateProxy(data, -1);
		}
	}

	@Override
	public String toString() {
		return "Thing [id=" + id + ", name=" + name + "]";
	}
}
