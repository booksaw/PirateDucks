public class Player {

    //Body is an object yet to be defined which will be defined as the main Player

    /**
     * Function for player movement
     *
     * Parameters:
     *  vel_x - velocity relative to its position change on the x axis
     *  vel_y - velocity relative to its position change on the y axis
     *
     *  Note: The values for vel_x and vel_y will be affected depending on the map size
     *  **/
    private void playerMovement() {
        float vel_x = xf;   //Replace the x to find optimal moving speed
        float vel_y = yf;   //Replace the y to find optimal moving speed

        batch.begin();
        if(Gdx.input.isKeyPressed(Input.Keys.W)) {
            if(Gdx.input.isKeyPressed(Input.Keys.A)) {
                body.setLinearVelocity(-vel_x,vel_y);
            }
            else if(Gdx.input.isKeyPressed(Input.Keys.D)) {
                body.setLinearVelocity(vel_x, vel_y);
            }
            else
                body.setLinearVelocity(0f, vel_y);
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.S)) {
            if(Gdx.input.isKeyPressed(Input.Keys.A)) {
                body.setLinearVelocity(-vel_x, -vel_y);
            }
            else if(Gdx.input.isKeyPressed(Input.Keys.D)) {
                body.setLinearVelocity(vel_x, -vel_y);
            }
            else
                body.setLinearVelocity(0f, -vel_y);
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.A)) {
            body.setLinearVelocity(-vel_x, 0f);
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.D)) {
            body.setLinearVelocity(vel_y, 0f);
        }
        else
            body.setLinearVelocity(0f, 0f);
    }

}
