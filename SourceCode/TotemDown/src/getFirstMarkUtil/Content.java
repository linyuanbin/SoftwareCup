package getFirstMarkUtil;
import java.util.ArrayList;
import java.util.List;

//对图片的JSon数据解析
public class Content { //1

	public Result result;

	public Result getResult() {
		return result;
	}
	
	public void setResult(Result result) {
		this.result = result;
	}
	
	public static class Result {  //2.1
		public List<Image> image=new ArrayList<>();
		public List<Text> text=new ArrayList<>();
		public List<Scene> scene=new ArrayList<>();
		public List<Pedestrian> pedestrian;

		public String getAllValue(){
			StringBuilder sb=new StringBuilder();
			int num=0;
			String tv=getAllTextValue();
			String iv=getAllImageValue();
			String sv=getAllSenceValue();
			if(!tv.equals("")){
				num++;
				sb.append(tv);
			}
			if(!iv.equals("")){
				if(num==1){
					sb.append("\n"+iv);
				}else{
					num++;
					sb.append(iv);
				}
			}
			
			if(!sv.equals("")){
				if(num!=0){
					sb.append("\n"+sv);
				}else{
					sb.append(sv);
				}
			}
			return sb.toString();
		}
		
		public String getAllTextValue(){
			if(text.size()!=0){
				StringBuilder sb=new StringBuilder();
				for(int i=0;i<text.size();i++){
					if(i==0){
						sb.append(text.get(i).getAttribute().getContent().getValue().replaceAll(" ",""));
					}else{
						sb.append("\n"+text.get(i).getAttribute().getContent().getValue().replaceAll(" ",""));
					}
				}
				return sb.toString();
			}else{
				return "";
			}
		}
		public String getAllImageValue(){
			if(image.size()!=0){
				StringBuilder sb=new StringBuilder();
				for(int i=0;i<image.size();i++){
					if(i==0){
						sb.append(image.get(i).getAttribute().getAllTagValue().replaceAll(" ",""));
					}else{
						sb.append("\n"+image.get(i).getAttribute().getAllTagValue().replaceAll(" ",""));
					}
				}
				return sb.toString();
			}else{
				return "";
			}
		}
		
		public String getAllSenceValue(){
			if(scene.size()!=0){
				StringBuilder sb=new StringBuilder();
				for(int i=0;i<scene.size();i++){
					if(i==0){
						sb.append(scene.get(i).getAttribute().getAllTagVaule().replaceAll(" ",""));
					}else{
						sb.append("\n"+scene.get(i).getAttribute().getAllTagVaule().replaceAll(" ",""));
					}
				}
				return sb.toString();
			}else{
				return "";
			}
		}
		
		
		public List<Image> getImage() {
			return image;
		}

		public void setImage(List<Image> image) {
			this.image = image;
		}

		public List<Text> getText() {
			return text;
		}

		public void setText(List<Text> text) {
			this.text = text;
		}

		public List<Scene> getScene() {
			return scene;
		}

		public void setScene(List<Scene> scene) {
			this.scene = scene;
		}

		public List<Pedestrian> getPedestrian() {
			return pedestrian;
		}

		public void setPedestrian(List<Pedestrian> pedestrian) {
			this.pedestrian = pedestrian;
		}

		public static class Image { //3.1
			public Attribute attribute;
			
			public Attribute getAttribute() {
				return attribute;
			}

			public void setAttribute(Attribute attribute) {
				this.attribute = attribute;
			}

			public static class Attribute{ //4.1
				public List<Tag> tag;
				
				public List<Tag> getTag() {
					return tag;
				}

				public void setTag(List<Tag> tag) {
					this.tag = tag;
				}
				
				public String getAllTagValue(){
					if(tag.size()!=0){
						StringBuilder sb=new StringBuilder();
						for(int i=0;i<tag.size();i++){
							if(i==0){
								sb.append(tag.get(i).getValue().replaceAll(" ",""));
							}else{
								sb.append("\n"+tag.get(i).getValue().replaceAll(" ",""));
							}
						}
						return sb.toString();
					}else{
						return "";
					}
				}

				public static class Tag{ //5.1
					public String value;

					public String getValue() {
						return value;
					}

					public void setValue(String value) {
						this.value = value;
					}
					
				}
				
			}
			
		}

		public static class Text { //3.2
			public Attribute attribute;
			
			public Attribute getAttribute() {
				return attribute;
			}

			public void setAttribute(Attribute attribute) {
				this.attribute = attribute;
			}

			public static class Attribute{
				public Contents content;
				
				public Contents getContent() {
					return content;
				}

				public void setContent(Contents content) {
					this.content = content;
				}

				public static class Contents{
					public String value;

					public String getValue() {
						return value;
					}

					public void setValue(String value) {
						this.value = value;
					}
				}
			}
		}

		public static class Scene {  //3.3
			public Attribute attribute;
			
			public Attribute getAttribute() {
				return attribute;
			}

			public void setAttribute(Attribute attribute) {
				this.attribute = attribute;
			}

			public static class Attribute{  //4.1
				public List<Tag> tag;
				public String getAllTagVaule(){
					if(tag.size()!=0){
						StringBuilder sb=new StringBuilder();
						for(int i=0;i<tag.size();i++){
							if(i==0){
							sb.append(tag.get(i).getValue().replaceAll(" ",""));
							}else{
								sb.append("\n"+tag.get(i).getValue().replaceAll(" ",""));
							}
						}
						return sb.toString();
					}else{
						return "";
					}
				}
				public List<Tag> getTag() {
					return tag;
				}
				public void setTag(List<Tag> tag) {
					this.tag = tag;
				}

				public class Tag{ //5.1
					public String value;

					public String getValue() {
						return value;
					}

					public void setValue(String value) {
						this.value = value;
					}
				}
			}
		}

		public static class Pedestrian {  //3.4
		}

	}

	
}
