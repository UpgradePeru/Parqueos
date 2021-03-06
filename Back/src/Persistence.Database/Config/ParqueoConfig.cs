using Microsoft.EntityFrameworkCore.Metadata.Builders;
using Model;

namespace Persistence.Database.Config
{
    public class ParqueoConfig
    {
        public ParqueoConfig(EntityTypeBuilder<Parqueo> entityBuilder)
        {
            entityBuilder.Property(x => x.Name).IsRequired().HasMaxLength(100);
            entityBuilder.Property(x => x.Description).IsRequired().HasMaxLength(250);
        }
    }
}
