package battleships.server;

public class BattleOverseer extends Thread {
	private Game myGame;
	private int remainingTime;
	private WaitingForConfState wfc=new WaitingForConfState(myGame);
	private DeployState deploy=new DeployState(myGame);
	private RoundState round=new RoundState(myGame);
	private UpdateState update=new UpdateState(myGame);
	
	private long startTime, estimatedTime;
	
	public BattleOverseer(Game game) {
		myGame=game;
	}
	@Override
	public void run() {
		
		try{
			while(!interrupted()){
				myGame.changeState(deploy);
			
			
				myGame.deployShipsInformation();
				sleep(500);
				myGame.resendDeployShipsInformation();
				sleep(500);
				myGame.resendDeployShipsInformation();
				myGame.deleteNotConfirmedPl();
				
				startTime=System.currentTimeMillis();
				sleep(myGame.getDeployTime());
				
				int roundCounter=0;
				while(!interrupted()){
					myGame.changeState(round);
					myGame.roundInformation(++roundCounter);
					startTime=System.currentTimeMillis();
					sleep(myGame.getRoundTime());
					
					myGame.changeState(update);
					myGame.updateInformation();
					sleep(3000);
					if(myGame.getNumOfRemaining()<=1)
						this.interrupt();
					
				}
			}
		}catch(InterruptedException e){}
	}
	
	public long getElapsedTime(){
		return System.currentTimeMillis()-startTime;
	}
}
