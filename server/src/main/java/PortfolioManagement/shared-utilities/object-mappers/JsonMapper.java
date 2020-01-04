package PortfolioManagement;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * utility class that maps User and Coin objects to JSON format for DB storage
 * and responses to client
 */

public class JsonMapper {

  public JSONObject mapUserJsonForDb(User user) {
    ArrayList<Coin> coins = user.getCoins();
    JSONArray coinsJsonMap = this.mapCoinsJson(coins, "DATABASE");

    JSONObject userJsonMap = new JSONObject();
    userJsonMap.put("user_id", user.getUserId());
    userJsonMap.put("coins", coinsJsonMap);
    return userJsonMap;
  }

  public JSONObject mapResponseJsonForClient(User user, String responseString, int responseCode, boolean withCoins) {
    JSONObject responseJsonMap = new JSONObject();

    if(withCoins) {
      ArrayList<Coin> coins = user.getCoins();
      double portfolioValueUsd = user.getPortfolioValueUsd();
      JSONArray coinsJsonMap = this.mapCoinsJson(coins, "CLIENT");
      responseJsonMap.put("response_string", responseString);
      responseJsonMap.put("response_code", responseCode);
      responseJsonMap.put("portfolio_value_usd", portfolioValueUsd);
      responseJsonMap.put("coins", coinsJsonMap);
    }
    else {
      responseJsonMap.put("response_string", responseString);
      responseJsonMap.put("response_code", responseCode);
    }

    return responseJsonMap;
  }

  private JSONArray mapCoinsJson(ArrayList<Coin> coins, String coinDestination) {
    JSONArray coinsJsonMap = new JSONArray();

    for(Coin currentCoin: coins) {
      JSONObject coinJson = new JSONObject();
      coinJson.put("coin_ticker", currentCoin.getTicker());
      coinJson.put("coin_amount", currentCoin.getAmount());

      if(coinDestination.equals("CLIENT")) {
        coinJson.put("latest_coin_price", currentCoin.getLatestPrice());
        coinJson.put("coin_holding_value_usd", currentCoin.getHoldingValueUsd());
      }

      coinsJsonMap.put(coinJson);
    }

    return coinsJsonMap;
  }

  public JSONObject mapRequestJsonForAuthServer(String authToken) {
    JSONObject requestJsonMap = new JSONObject();
    requestJsonMap.put("crypto_cost_session", authToken);
    return requestJsonMap;
  }

}