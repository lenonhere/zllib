package com.zl.base.core.taglib;

import java.io.InputStream;
import java.util.Properties;

import common.Logger;

public class ReadTaglibProperty {
	static final Logger log = Logger.getLogger(ReadTaglibProperty.class);
	private static ReadTaglibProperty instance;
	private String gridExpandImage = "public/images/expand.gif";
	private String gridShrinkImage = "public/images/shrink.gif";
	private String gridSpaceImage = "public/images/space.gif";
	private String gridheadbgcolor = "#5aa3d4";
	private String gridbordercolor = "#000000";
	private String gridbgcolor = "#eff5f9";
	private String gridselectcolor = "#99ccff";
	private String gridrowheadbgcolor = "#5aa3d4";
	private String gridlinecolor = "#736a59";
	private String gridrowheight = "20";
	private String gridheightscale = "1";
	private String gridwidthscale = "1";
	private String gridheadfontsize = "12";
	private String gridfontsize = "12";
	//
	private String chartheightscale = "1";
	private String chartwidthscale = "1";
	private String chartfontsize = "12";
	//
	private String treeimagepath = "public/xtree/";
	private String treeselectedicon = "select.bmp";
	private String treenoselectedicon = "noselect.bmp";
	private String treenoicon = "no.bmp";
	private String treerooticon = "foldericon.png";
	private String treeopenrooticon = "openfoldericon.png";
	private String treefoldericon = "foldericon.png";
	private String treeopenfoldericon = "openfoldericon.png";
	private String treefileicon = "file.png";
	private String treeiicon = "I.png";
	private String treelicon = "L.png";
	private String treelminusicon = "Lminus.png";
	private String treelplusicon = "Lplus.png";
	private String treeticon = "T.png";
	private String treetminusicon = "Tminus.png";
	private String treetplusicon = "Tplus.png";
	private String treeblankicon = "blank.png";
	private int showrowcount = -1;

	private ReadTaglibProperty() {
		Properties properties = new Properties();
		String str = null;
		try {
			InputStream is = getClass().getResourceAsStream(
					"/taglib.properties");
			properties.load(is);
		} catch (Exception e) {
			log.error("no properties file found found for taglib.properties");
		}
		str = properties.getProperty("grid.spaceimage");
		if (str != null)
			this.gridSpaceImage = str;
		str = properties.getProperty("grid.shrinkimage");
		if (str != null)
			this.gridShrinkImage = str;
		str = properties.getProperty("grid.expandimage");
		if (str != null)
			this.gridExpandImage = str;
		str = properties.getProperty("grid.linecolor");
		if (str != null)
			this.gridlinecolor = str;
		str = properties.getProperty("grid.headbgcolor");
		if (str != null)
			this.gridheadbgcolor = str;
		str = properties.getProperty("grid.bgcolor");
		if (str != null)
			this.gridbgcolor = str;
		str = properties.getProperty("grid.bordercolor");
		if (str != null)
			this.gridbordercolor = str;
		str = properties.getProperty("grid.selectcolor");
		if (str != null)
			this.gridselectcolor = str;
		str = properties.getProperty("grid.rowheadbgcolor");
		if (str != null)
			this.gridrowheadbgcolor = str;
		str = properties.getProperty("grid.rowheight");
		if (str != null)
			this.gridrowheight = str;
		str = properties.getProperty("grid.heightscale");
		if (str != null)
			this.gridheightscale = str;
		str = properties.getProperty("grid.widthscale");
		if (str != null)
			this.gridwidthscale = str;
		str = properties.getProperty("grid.headfontsize");
		if (str != null)
			this.gridheadfontsize = str;
		str = properties.getProperty("grid.fontsize");
		if (str != null)
			this.gridfontsize = str;
		str = properties.getProperty("grid.showrowcount");
		if (str != null)
			this.showrowcount = Integer.parseInt(str);
		str = properties.getProperty("chart.heightscale");
		if (str != null)
			this.chartheightscale = str;
		str = properties.getProperty("chart.widthscale");
		if (str != null)
			this.chartwidthscale = str;
		str = properties.getProperty("chart.fontsize");
		if (str != null)
			this.chartfontsize = str;
		str = properties.getProperty("tree.imagepath");
		if (str != null)
			this.treeimagepath = str;
		str = properties.getProperty("tree.selectedicon");
		if (str != null)
			this.treeselectedicon = str;
		str = properties.getProperty("tree.noselectedicon");
		if (str != null)
			this.treenoselectedicon = str;
		str = properties.getProperty("tree.noicon");
		if (str != null)
			this.treenoicon = str;
		str = properties.getProperty("tree.rooticon");
		if (str != null)
			this.treerooticon = str;
		str = properties.getProperty("tree.openrooticon");
		if (str != null)
			this.treeopenrooticon = str;
		str = properties.getProperty("tree.foldericon");
		if (str != null)
			this.treefoldericon = str;
		str = properties.getProperty("tree.openfoldericon");
		if (str != null)
			this.treeopenfoldericon = str;
		str = properties.getProperty("tree.fileicon");
		if (str != null)
			this.treefileicon = str;
		str = properties.getProperty("tree.iicon");
		if (str != null)
			this.treeiicon = str;
		str = properties.getProperty("tree.licon");
		if (str != null)
			this.treelicon = str;
		str = properties.getProperty("tree.lminusicon");
		if (str != null)
			this.treelminusicon = str;
		str = properties.getProperty("tree.lplusicon");
		if (str != null)
			this.treelplusicon = str;
		str = properties.getProperty("tree.ticon");
		if (str != null)
			this.treeticon = str;
		str = properties.getProperty("tree.tminusicon");
		if (str != null)
			this.treetminusicon = str;
		str = properties.getProperty("tree.tplusicon");
		if (str != null)
			this.treetplusicon = str;
		str = properties.getProperty("tree.blankicon");
		if (str != null)
			this.treeblankicon = str;
	}

	public static synchronized ReadTaglibProperty getInstance() {
		if (instance == null)
			instance = new ReadTaglibProperty();
		return instance;
	}

	public String getgridheadbgcolor() {
		return this.gridheadbgcolor;
	}

	public String getgridbgcolor() {
		return this.gridbgcolor;
	}

	public String getgridbordercolor() {
		return this.gridbordercolor;
	}

	public String getgridselectcolor() {
		return this.gridselectcolor;
	}

	public String getgridrowheadbgcolor() {
		return this.gridrowheadbgcolor;
	}

	public String getgridlinecolor() {
		return this.gridlinecolor;
	}

	public int getgridrowheight() {
		try {
			return Integer.parseInt(this.gridrowheight);
		} catch (Exception e) {
		}
		return 20;
	}

	public int getgridwidthscale() {
		try {
			return Integer.parseInt(this.gridwidthscale);
		} catch (Exception e) {
		}
		return 1;
	}

	public int getgridheightscale() {
		try {
			return Integer.parseInt(this.gridheightscale);
		} catch (Exception e) {
		}
		return 1;
	}

	public String getgridheadfontsize() {
		return this.gridheadfontsize;
	}

	public int getgridfontsize() {
		try {
			return Integer.parseInt(this.gridfontsize);
		} catch (Exception e) {
		}
		return 12;
	}

	public int getchartheightscale() {
		try {
			return Integer.parseInt(this.chartheightscale);
		} catch (Exception e) {
		}
		return 1;
	}

	public int getchartwidthscale() {
		try {
			return Integer.parseInt(this.chartwidthscale);
		} catch (Exception e) {
		}
		return 1;
	}

	public int getchartfontsize() {
		try {
			return Integer.parseInt(this.chartfontsize);
		} catch (Exception e) {
		}
		return 12;
	}

	public String gettreeimagepath() {
		return this.treeimagepath;
	}

	public String gettreeselectedicon() {
		return this.treeselectedicon;
	}

	public String gettreenoselectedicon() {
		return this.treenoselectedicon;
	}

	public String gettreenoicon() {
		return this.treenoicon;
	}

	public String gettreerooticon() {
		return this.treerooticon;
	}

	public String gettreeopenrooticon() {
		return this.treeopenrooticon;
	}

	public String gettreefoldericon() {
		return this.treefoldericon;
	}

	public String gettreeopenfoldericon() {
		return this.treeopenfoldericon;
	}

	public String gettreefileicon() {
		return this.treefileicon;
	}

	public String gettreeiicon() {
		return this.treeiicon;
	}

	public String gettreelicon() {
		return this.treelicon;
	}

	public String gettreelminusicon() {
		return this.treelminusicon;
	}

	public String gettreelplusicon() {
		return this.treelplusicon;
	}

	public String gettreeticon() {
		return this.treeticon;
	}

	public String gettreetminusicon() {
		return this.treetminusicon;
	}

	public String gettreetplusicon() {
		return this.treetplusicon;
	}

	public String gettreeblankicon() {
		return this.treeblankicon;
	}

	public String getgridexpandimage() {
		return this.gridExpandImage;
	}

	public void setgridexpandimage(String paramString) {
		this.gridExpandImage = paramString;
	}

	public String getgridshrinkimage() {
		return this.gridShrinkImage;
	}

	public void setgridshrinkimage(String paramString) {
		this.gridShrinkImage = paramString;
	}

	public String getgridspaceimage() {
		return this.gridSpaceImage;
	}

	public void setgridspaceimage(String paramString) {
		this.gridSpaceImage = paramString;
	}

	public int getgridshowrowcount() {
		return this.showrowcount;
	}

	public void setgridshowrowcount(int paramInt) {
		this.showrowcount = paramInt;
	}
}
