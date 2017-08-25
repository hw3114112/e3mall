package cn.my.e3mall.common.pojo;
import java.io.Serializable;
import java.util.List;
/**
 * EasyUI DataGrid json格式包装类
 * @author hw311
 *
 */
public class DataGridResult implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long total;

    private List<?> rows;

	public final Long getTotal() {
		return total;
	}

	public final void setTotal(Long total) {
		this.total = total;
	}

	public final List<?> getRows() {
		return rows;
	}

	public final void setRows(List<?> rows) {
		this.rows = rows;
	}

	public DataGridResult() {
		super();
	}

	public DataGridResult(long total, List<?> rows) {
		super();
		this.total = total;
		this.rows = rows;
	}
    
	
}