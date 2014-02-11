package org.ourgrid.node.idleness;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import com.sun.jna.Native;
import com.sun.jna.Structure;
import com.sun.jna.win32.StdCallLibrary;

public class Win32IdlenessDetector extends AbstractIdlenessDetector {

	public Win32IdlenessDetector(Properties properties) {
		super(properties);
	}
	
	public interface Kernel32 extends StdCallLibrary {
		Kernel32 INSTANCE = (Kernel32) Native.loadLibrary("kernel32",
				Kernel32.class);

		public int GetTickCount();
	};

	public interface User32 extends StdCallLibrary {
		User32 INSTANCE = (User32) Native.loadLibrary("user32", User32.class);

		public static class LASTINPUTINFO extends Structure {
			public int cbSize = 8;

			// / Tick count of when the last input event was received.
			public int dwTime;

			@Override
			protected List<String> getFieldOrder() {
				return Arrays.asList(new String[] {"cbSize", "dwTime"});
			}
		}

		public boolean GetLastInputInfo(LASTINPUTINFO result);
	};
	
	private static int getIdleTimeMillis() {
        User32.LASTINPUTINFO lastInputInfo = new User32.LASTINPUTINFO();
        User32.INSTANCE.GetLastInputInfo(lastInputInfo);
        return Kernel32.INSTANCE.GetTickCount() - lastInputInfo.dwTime;
    }

	// Idleness time is given in seconds.
	@Override
	protected boolean checkIdle(Long idlenessTime) {
		if (!isEnabled()) {
			return true;
		}
		try {
			return getIdleTimeMillis() > idlenessTime * 1000; 
		} catch (Exception e) {
			return false;
		}
	}
}
