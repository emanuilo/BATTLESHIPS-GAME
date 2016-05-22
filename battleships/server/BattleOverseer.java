package battleships.server;

public class BattleOverseer extends Thread {
	private Game myGame;
	private int remainingTime;
	private WaitingForConfState wfc=new WaitingForConfState(myGame);
	private DeployState deploy;
	private RoundState round;
	private UpdateState update;
	
	private long startTime, estimatedTime;
	
	public BattleOverseer(Game game) {
		myGame=game;
		deploy=new DeployState(myGame);
		round=new RoundState(myGame);
		update=new UpdateState(myGame);
	}
	@Override
	public void run() {
		
		try{
			while(!isInterrupted()){
				System.out.println("usao u overseer");
				myGame.changeState(deploy);
			
				sleep(500);
				myGame.deployShipsInformation();
				sleep(500);
				myGame.resendDeployShipsInformation();
				sleep(500);
				myGame.resendDeployShipsInformation();
				myGame.deleteNotConfirmedPl();
				
				startTime=System.currentTimeMillis();
				sleep(myGame.getDeployTime());
				
				int roundCounter=0;
				while(!isInterrupted()){
					myGame.changeState(round);
					myGame.roundInformation(++roundCounter);
					startTime=System.currentTimeMillis();
					sleep(myGame.getRoundTime());
					
					myGame.changeState(update);
					myGame.updateInformation();
					myGame.updating();
					sleep(3000);
					if(myGame.getNumOfRemaining()<=1)
						this.interrupt();
					
				}
			}
		}catch(InterruptedException e){}
	}
	
	public long getElapsedTime(){
		if (startTime!=0) return System.currentTimeMillis()-startTime;
		else return 0;
	}
}
