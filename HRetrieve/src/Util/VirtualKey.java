package Util;

import java.awt.Robot;
import java.awt.event.KeyEvent;

public class VirtualKey {
//ctrl+key
public static void KeyPressWithCtrl(Robot r,int key){
	r.keyPress(KeyEvent.VK_CONTROL);
	r.keyPress(key);
	r.keyRelease(key);
	r.keyRelease(KeyEvent.VK_CONTROL);
	r.delay(100);
}
//ctrl+key
public static void KeyPressWithAlt(Robot r,int key){
	r.keyPress(KeyEvent.VK_ALT);
	r.keyPress(key);
	r.keyRelease(key);
	r.keyRelease(KeyEvent.VK_ALT);
	r.delay(100);
}
}
