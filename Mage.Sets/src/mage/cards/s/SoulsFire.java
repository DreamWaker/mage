package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.TargetControlledDealsDamageAnyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetAnyTarget;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author North
 */
public final class SoulsFire extends CardImpl {

    public SoulsFire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{R}");

        // Target creature you control on the battlefield deals damage equal to its power to any target.
        this.getSpellAbility().addEffect(new TargetControlledDealsDamageAnyTargetEffect());
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        this.getSpellAbility().addTarget(new TargetAnyTarget());
    }

    private SoulsFire(final SoulsFire card) {
        super(card);
    }

    @Override
    public SoulsFire copy() {
        return new SoulsFire(this);
    }
}
