package br.com.desafio_neki.dto.imgbb;

public class ImgBBDTO {
	private Data data;

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "ImgBBDTO [data=" + data + "]";
	}
}