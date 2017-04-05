package serie06.model;

import java.util.Random;

public class StdProducer extends AbstractActor {

	public StdProducer(Box box, int max, String name) {
		super(box, max, name);
	}

	@Override
	protected boolean canUseBox() {
		return getBox().isEmpty();
	}

	@Override
	protected void useBox() {
		Random r = new Random();
		int rand = r.nextInt(10);
		getBox().fill(rand);
		fireSentenceSaid("j'ai mis : " + rand);
	}

}
