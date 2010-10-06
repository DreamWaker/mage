/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 * 
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 * 
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 * 
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 * 
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * 
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */

package mage.abilities.effects.common;

import mage.Constants.Outcome;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class PutOnLibraryTargetEffect extends OneShotEffect<PutOnLibraryTargetEffect> {

	boolean onTop;

	public PutOnLibraryTargetEffect(boolean onTop) {
		super(Outcome.ReturnToHand);
		this.onTop = onTop;
	}

	public PutOnLibraryTargetEffect(final PutOnLibraryTargetEffect effect) {
		super(effect);
		this.onTop = effect.onTop;
	}

	@Override
	public PutOnLibraryTargetEffect copy() {
		return new PutOnLibraryTargetEffect(this);
	}

	@Override
	public boolean apply(Game game, Ability source) {
		switch (source.getTargets().get(0).getZone()) {
			case BATTLEFIELD:
				Permanent permanent = game.getPermanent(source.getFirstTarget());
				if (permanent != null) {
					return permanent.moveToZone(Zone.LIBRARY, game, onTop);
				}
			case GRAVEYARD:
				Card card = game.getCard(source.getFirstTarget());
				for (Player player: game.getPlayers().values()) {
					if (player.getGraveyard().contains(card.getId())) {
						player.getGraveyard().remove(card);
						return card.moveToZone(Zone.LIBRARY, game, onTop);
//						if (onTop)
//							player.getLibrary().putOnTop(card, game);
//						else
//							player.getLibrary().putOnBottom(card, game);
//						return true;
					}
				}
		}
		return false;
	}

	@Override
	public String getText(Ability source) {
		StringBuilder sb = new StringBuilder();
		sb.append("Put target ").append(source.getTargets().get(0).getTargetName()).append(" on ");
		sb.append(onTop?"top":"the bottom").append(" of it's owner's library");
		return sb.toString();

	}
}
