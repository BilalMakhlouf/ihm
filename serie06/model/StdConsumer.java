package serie06.model;

public class StdConsumer extends AbstractActor {

	public StdConsumer(Box box, int max, String name) {
		super(box, max, name);
	}

	@Override
	protected boolean canUseBox() {
		return !getBox().isEmpty();
	}

	@Override
	protected void useBox() {
		fireSentenceSaid("j'ai pris : " + getBox().getValue());
		getBox().dump();
	}

}
