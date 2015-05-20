package th.co.truecorp.vcare.http.servlet;

import java.io.File;

import java.io.FileInputStream;

import java.io.IOException;

import java.io.InputStream;

import java.net.URL;

import java.util.Enumeration;

import java.util.Vector;

import java.util.jar.JarEntry;

import java.util.jar.JarFile;

import th.co.truecorp.vcare.util.Logger;

public class HttpServletClassLoader extends ClassLoader {
	private Logger log = Logger.getLogger(HttpServletClassLoader.class
			.getName());
	private String _appPath = null;

	private static final String[] FIND_CLASS_PATHS = { "/", "/WEB-INF/classes",
			"/classes" };
	private static final String[] FIND_LIB_PATHS = { "/WEB-INF/lib", "/lib" };

	public HttpServletClassLoader(ClassLoader parent, String app_path) {
		super(parent);
		this._appPath = app_path;
	}

	public Class<?> findClass(String classname) throws ClassNotFoundException {

		String class_filename = classname.replace('.', '/') + ".class";

		for (int i = 0; i < FIND_CLASS_PATHS.length; i++) {

			String prefix_path = this._appPath + FIND_CLASS_PATHS[i];
			
			File class_file = new File(prefix_path + "/" + class_filename);

			if (class_file.exists()) {
				byte[] class_buffer = class_buffer = loadFile(class_file);
				return defineClass(classname, class_buffer, 0,
						class_buffer.length);
			}
		}

		for (int i = 0; i < FIND_LIB_PATHS.length; i++) {
			String prefix_path = this._appPath + FIND_LIB_PATHS[i];
			
			File libdir_file = new File(prefix_path);
			if (libdir_file.exists()) {
				String[] listfiles = libdir_file.list();
				for (int j = 0; j < listfiles.length; j++) {
					if (listfiles[j].endsWith(".jar")) {
						byte[] class_buffer = loadJarFile(prefix_path + "/"
								+ listfiles[j], class_filename);
						if (class_buffer != null && class_buffer.length!=0) {
							log.logInformation(">> classname: "+classname+" ,class_buffer.length:"+class_buffer.length);
							return defineClass(classname, class_buffer, 0, class_buffer.length);
						}
					}
				}
			}
		}
		throw new ClassNotFoundException(classname);
	}

	private byte[] loadFile(File file) {
		InputStream filein = null;
		try {
			filein = new FileInputStream(file);
			int file_size = (int) file.length();
			return loadFromInputStream(filein, file_size);
		} catch (Exception e) {
			return null;
		} finally {
			if (filein != null)
				try {
					filein.close();
				} catch (Exception e) {
				}
		}
	}

	protected URL findResource(String name) {
		try {
			for (int i = 0; i < FIND_CLASS_PATHS.length; i++) {
				String fullpath = this._appPath + FIND_CLASS_PATHS[i] + "/" + name;
				File resource_file = new File(fullpath);
				if (resource_file.exists()) {
					return resource_file.toURL();
				}
			}

			for (int i = 0; i < FIND_LIB_PATHS.length; i++) {
				String fullpath = this._appPath + FIND_LIB_PATHS[i];

				File libdir_file = new File(fullpath);

				if (libdir_file.exists()) {
					String[] listfiles = libdir_file.list();

					for (int j = 0; j < listfiles.length; j++) {
						if (!listfiles[j].endsWith(".jar"))
							continue;
						JarFile jarfile = new JarFile(fullpath + "/"
								+ listfiles[j]);
						try {
							JarEntry jarentry = jarfile.getJarEntry(name);
							if (jarentry != null) {
								fullpath = fullpath.replace('\\', '/');
								URL jarfile_url = new URL("jar:file:/"
										+ fullpath + "/" + listfiles[j] + "!/");
								URL localURL1 = new URL(jarfile_url, name);
								return localURL1;
							}
						} finally {
							if (jarfile != null)
								jarfile.close();
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	protected Enumeration<URL> findResources(String name) throws IOException {
		Vector<URL> url_vec = new Vector<URL>();
		try {
			for (int i = 0; i < FIND_CLASS_PATHS.length; i++) {
				String fullpath = this._appPath + FIND_CLASS_PATHS[i] + "/"
						+ name;
				File resource_file = new File(fullpath);
				if (resource_file.exists()) {
					url_vec.add(resource_file.toURL());
				}
			}
			for (int i = 0; i < FIND_LIB_PATHS.length; i++) {
				String fullpath = this._appPath + FIND_LIB_PATHS[i];

				File libdir_file = new File(fullpath);

				if (libdir_file.exists()) {
					String[] listfiles = libdir_file.list();

					for (int j = 0; j < listfiles.length; j++) {
						if (!listfiles[j].endsWith(".jar"))
							continue;
						JarFile jarfile = new JarFile(fullpath + "/"
								+ listfiles[j]);
						try {
							JarEntry jarentry = jarfile.getJarEntry(name);
							if (jarentry != null) {
								fullpath = fullpath.replace('\\', '/');
								URL jarfile_url = new URL("jar:file:"
										+ fullpath + "/" + listfiles[j] + "!/");
								url_vec.add(new URL(jarfile_url, name));
							}
						} finally {
							if (jarfile != null)
								jarfile.close();
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return url_vec.elements();
	}

	private byte[] loadJarFile(String filename, String class_name) {
		JarFile jarfile = null;
		InputStream filein = null;
		int file_size = 0;
		try {
			jarfile = new JarFile(filename);
			JarEntry jarentry = jarfile.getJarEntry(class_name);
			if (jarentry == null) {
			} else {
				file_size = (int) jarentry.getSize();
				filein = jarfile.getInputStream(jarentry);
			}

			return loadFromInputStream(filein, file_size);
		} catch (Exception e) {

			e.printStackTrace();
			return null;
		} finally {
			if (filein != null)
				try {
					filein.close();
				} catch (Exception e) {
				}
			if (jarfile != null)
				try {
					jarfile.close();
				} catch (Exception e) {
				}
		}
	}

	private byte[] loadFromInputStream(InputStream file_in, int file_size)
			throws IOException {
		byte[] binary_class = null;
		binary_class = new byte[file_size];
		int total_size = 0;
		while (total_size < file_size) {
			int read_size = file_in.read(binary_class, total_size, file_size
					- total_size);
			total_size += read_size;
		}
		return binary_class;
	}

	protected Class<?> loadClass(String classname, boolean resolve)
			throws ClassNotFoundException {
		Class<?> loaded_class = findLoadedClass(classname);
		if (loaded_class == null) {
			ClassLoader parent_cl = getParent();
			if (parent_cl == null)
				parent_cl = getSystemClassLoader();
			try {
				loaded_class = parent_cl.loadClass(classname);
			} catch (ClassNotFoundException e) {
				loaded_class = findClass(classname);
			}
		}

		if ((loaded_class != null) && (resolve)) {
			resolveClass(loaded_class);
		}
		return loaded_class;
	}

	public URL getResource(String resname) {
		URL res_url = findResource(resname);

		if (res_url != null)
			return res_url;

		ClassLoader parent_cl = getParent();
		if (parent_cl == null) {
			parent_cl = getSystemClassLoader();
		}
		return parent_cl.getResource(resname);
	}

}
