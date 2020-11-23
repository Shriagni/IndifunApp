package com.deificindia.indifun1.ui.cam;

/**
 * Interface that needs to be implemented on activities that
 * inflate layouts containing a CameraView widget, so that
 * the widget can obtain its CameraHost immediately.
 */
public interface CameraHostProvider {
  /**
   * @return the CameraHost to be used by the CameraView
   */
  CameraHost getCameraHost();
}
