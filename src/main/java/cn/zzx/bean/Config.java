package cn.zzx.bean;

import cn.zzx.util.ObjectUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author fzh
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Config implements Validatable{
	private BigDecimal price;

	@Override
	public boolean canInsert() {
		return false;
	}

	@Override
	public boolean canUpdate() {
		return true;
	}

	@Override
	public boolean canDelete() {
		return false;
	}

	@Override
	public boolean canSelect() {
		return true;
	}
}
