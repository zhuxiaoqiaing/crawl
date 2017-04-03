package document;
import java.io.IOException;

import SendMail.TimingMail;
import Util.FileZip;
import Util.PropertyUtil;

import com.lowagie.text.DocListener;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Rectangle;
public  class docListener implements DocListener{
	@Override
	public void close() {
	try {
		FileUtil.zipFile(PropertyUtil.getPro("DataSource"));
	} catch (IOException e) {	
		System.out.println("zip error");}
		TimingMail.Send();
		System.out.println("send mail and close the document successfully");
	}
	@Override
	public boolean newPage() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void open() {
		// TODO Auto-generated method stub
		System.out.println("open");
	}
	@Override
	public void resetFooter() {
		// TODO Auto-generated method stub
				}
	@Override
	public void resetHeader() {
		// TODO Auto-generated method stub
				}
	@Override
	public void resetPageCount() {
		// TODO Auto-generated method stub
				}
	@Override
	public void setFooter(HeaderFooter arg0) {
		// TODO Auto-generated method stub
				}
	@Override
	public void setHeader(HeaderFooter arg0) {
		// TODO Auto-generated method stub
				}
	@Override
	public boolean setMarginMirroring(boolean arg0) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean setMarginMirroringTopBottom(boolean arg0) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean setMargins(float arg0, float arg1, float arg2, float arg3) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void setPageCount(int arg0) {
		// TODO Auto-generated method stub
				}
	@Override
	public boolean setPageSize(Rectangle arg0) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean add(Element arg0) throws DocumentException {
		// TODO Auto-generated method stub
		return false;
	}
		}
