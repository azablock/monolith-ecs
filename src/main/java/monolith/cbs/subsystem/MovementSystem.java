package monolith.cbs.subsystem;

import monolith.cbs.subsystem.tools.vm_translator.VMTranslator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MovementSystem implements MoSubSystem {

  @Autowired
  private VMTranslator vmTranslator;

  @Override
  public void processOneGameTick() {

  }
}
